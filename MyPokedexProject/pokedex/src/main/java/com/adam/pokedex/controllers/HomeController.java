package com.adam.pokedex.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.adam.pokedex.models.*;
import com.adam.pokedex.services.*;

@Controller
public class HomeController {
	@Autowired
	private UserService userService;
	@Autowired
	private PokemonService pokeService;

	
// REGISTER User
	@RequestMapping("/")
	public String registerForm(@ModelAttribute("user") User user, HttpSession session) {
		if (session.getAttribute("userId")!=null) {
			Long currentUserId = (Long) session.getAttribute("userId");
			return "redirect:/show/mypokedex/"+currentUserId;
		}
		return "authentication/HomePage.jsp";
	}
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registerUser(Model model, @Valid @ModelAttribute("user") User user, BindingResult result,
			HttpSession session) {
		// if there are errors with registration,
		// add error message and return the registration page
		if (result.hasErrors()) {
			model.addAttribute("register_error", "Couldn't register user. Please try again.");
			return "authentication/HomePage.jsp";
		} else if (userService.findByEmail(user.getEmail()) != null) {
			model.addAttribute("register_error", "That email is already in use. Please try again.");
			return "authentication/HomePage.jsp";
		} else if (!user.getPassword().equals(user.getPasswordConfirmation())) {
			model.addAttribute("register_error", "Passwords don't match. Please try again.");
			return "authentication/HomePage.jsp";
			// else, register the user and save the user.id to session as userId
		} else {
			User u = userService.registerUser(user);
			session.setAttribute("userId", u.getId());
			return "redirect:/findpokemon";
		}
	}

// LOGIN/LOGOUT User
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model,
			HttpSession session) {
		// if the user is authenticated, save their user id in session
		if (userService.authenticateUser(email, password)) {
			session.setAttribute("userId", userService.findByEmail(email).getId());
			return "redirect:/findpokemon";
		}
		// else, add error messages and return the login page
		else {
			model.addAttribute("login_error", "User authentication error. Please try again.");
			return "authentication/HomePage.jsp";
		}
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		// invalidate session and redirect to login page
		session.invalidate();
		return "redirect:/";
	}

// FIND Pokemon functionality
	 @RequestMapping("/findpokemon")
	public String search(Model model, HttpSession session) {
		// get user from session, save them in the model and return requested page
		// if no user, return to login
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "redirect:/";
		} else {
			model.addAttribute("user", userService.findUserById(currentUserId));
			pokeService.addSearchPokemonInfoToModelFromSession(model, session);
			return "pokedex/findPokemon.jsp";
		}
	}
	@PostMapping("/findpokemon")
	public String searchPokemon(
			@NotBlank @RequestParam("query") String query,
			Model model,
			HttpSession session) {
		// get user from session, save them in the model and do post
		// if no user, return to login
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "redirect:/";
		// check if the query is empty
		} else if (query.isBlank()) {
			model.addAttribute("error","You haven't given me a pokemon to search for!");
			session.removeAttribute("searchPokemonResult");
			return "pokedex/findPokemon.jsp";
		} else {
			// Find pokemon using PokeApi.
			pokeService.findPokemonFromWebAndAddToSession(query, model, session);
			return "redirect:/findpokemon";
		}
	}	

// ADD Pokemon Functionality
	@RequestMapping("/addpokemon")
	public String addPokemonForm(Model model, HttpSession session) {
		// get user from session, save them in the model and return requested page
		// if no user, return to login
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "redirect:/";
		} else {
			model.addAttribute("user", userService.findUserById(currentUserId));
			pokeService.addSearchPokemonInfoToModelFromSession(model, session);
			return "pokedex/addPokemon.jsp";
		}
	}
	@PostMapping("/addpokemonwithname")
	public String addPokemonWithName(
			@RequestParam("name") String name,
			Model model,
			HttpSession session) {
		// if no user, return to login
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "redirect:/";
		} else if (name.isBlank()) {
			model.addAttribute("error","You haven't given me a name for your pokemon!");
			model.addAttribute("user", userService.findUserById(currentUserId));
			pokeService.addSearchPokemonInfoToModelFromSession(model, session);
			return "pokedex/addPokemon.jsp";
		} else {
			pokeService.createPokemonWithName(name, session);
			return "redirect:/show/mypokedex/"+currentUserId;
		}
	}
	@PostMapping("/addpokemonwithoutname")
	public String addPokemonWithoutName(HttpSession session) {
		// if no user, return to login
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "redirect:/";
		} else {
			pokeService.createPokemonWithoutName(session);
			return "redirect:/show/mypokedex/"+currentUserId;
		}
	}

// EDIT Pokemon Name
	@GetMapping("/rename/{id}")
	public String editPokemonForm(
			@PathVariable("id") Long pokeId, 
			Model model, 
			HttpSession session) {
		// get user from session, save them in the model and return requested page
		// if no user, return to login
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "redirect:/";
		// check if poke exists in repo
		} else if (pokeService.getPokemonById(pokeId)==null) {
			return "redirect:/show/mypokemon/"+currentUserId;
		// add pokemon to model and render jsp
		} else {
			model.addAttribute("user", userService.findUserById(currentUserId));
			model.addAttribute("editPokemon", pokeService.getPokemonById(pokeId));
			return "pokedex/EditPokemon.jsp";
		}
	}
	@PutMapping("/rename/{id}")
	public String renamePokemon(
			@PathVariable("id") Long pokeId,
			@RequestParam("name") String name,
			Model model,
			HttpSession session) {
		// get user from session, save them in the model and return requested page
		// if no user, return to login
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "redirect:/";
		//check if poke exists in repo
		} else if (pokeService.getPokemonById(pokeId)==null) {
			return "redirect:/show/mypokemon/"+currentUserId;
		// make sure we can't release someone else's pokemon
		} else if(!pokeService.getPokemonById(pokeId).getOwner().getId().equals(currentUserId)) {
			return "redirect:/show/mypokemon/"+currentUserId;
		// else release pokemon and return to pokedex
		} else if (name.isBlank()) {
			model.addAttribute("error","You haven't given me a new name for your pokemon!");
			return "redirect:/show/mypokemon/"+currentUserId;
		} else {
			//rename and return to pokemon screen
			Pokemon poke = pokeService.getPokemonById(pokeId);
			pokeService.renamePokemon(poke, name);
			return "redirect:/show/mypokemon/"+currentUserId;
		}
	}
	
// RELEASE Pokemon Functionality
	@DeleteMapping("/release/{id}")
	public String releasePokemon(@PathVariable("id") Long pokeId, HttpSession session) {
		// get user from session, save them in the model and return requested page
		// if no user, return to login
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "redirect:/";
		//check if poke exists in repo
		} else if (pokeService.getPokemonById(pokeId)==null) {
			System.out.println(pokeId);
			return "redirect:/";
		// make sure we can't release someone else's pokemon
		} else if(!pokeService.getPokemonById(pokeId).getOwner().getId().equals(currentUserId)) {
			return "redirect:/";
		// else release pokemon and return to pokedex
		} else {
			pokeService.releasePokemon(pokeId);
			return "redirect:/show/mypokemon/"+currentUserId;
		}
	}

// LIKE Another User's Pokedex
	@GetMapping("/like/{id}")
	public String likePokedex(
			@PathVariable("id") Long pokedexId, 
			HttpSession session, 
			Model model) {
		// get user from session, save them in the model and return requested page
		// if no user, return to login
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "redirect:/show/users";
		} else {
			userService.likeAPokedexById(currentUserId, pokedexId);
			return "redirect:/show/users";
		}
	}
	
// DISPLAY Functionality
	// Main MyPokedex screen (Home Page)
	@RequestMapping("/show/mypokedex/{id}")
	public String showPokedex(Model model, HttpSession session, @PathVariable String id) {
		// get user from session, save them in the model and return requested page
		// if no user, return to login
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "redirect:/";
		} else {
			// check if userId is blank. Then return current user's pokedex
			Long longId = Long.valueOf(id).longValue(); // convert id to Long
			if (!currentUserId.equals(longId)) {
				model.addAttribute("pokedexOwner", userService.findUserById(longId).getDisplayName());
				model.addAttribute("pokemonlist", userService.findUserById(longId).getPokemonRoster());
				model.addAttribute("user", userService.findUserById(currentUserId));
				return "pokedex/OtherPokedex.jsp";
			} else if(userService.findUserById(longId)==null) {
				longId=currentUserId;
				return "redirect:/show/mypokedex/"+longId;
			}
			// get user's pokemon list to be displayed
			model.addAttribute("pokemonlist", userService.findUserById(longId).getPokemonRoster());
			model.addAttribute("user", userService.findUserById(currentUserId));
			return "pokedex/MyPokedex.jsp";
		}
	}
	// My Pokemon screen (only user can see their pokemon details)
	@RequestMapping("/show/mypokemon/{id}")
	public String showPokemon(Model model, HttpSession session, @PathVariable String id) {
		// get user from session, save them in the model and return requested page
		// if no user, return to login
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "redirect:/";
		} else {
			// check if userId is blank. Then return current user's pokedex
			Long longId = Long.valueOf(id).longValue(); // convert id to Long
			if(userService.findUserById(longId)==null) {
				System.out.println(userService.findUserById(longId));
				longId=currentUserId;
				return "redirect:/show/mypokedex/"+longId;
			// logged in user can ONLY access their pokemon screen
			} else if (!longId.equals(currentUserId)) {
				return "redirect:/show/mypokemon/"+currentUserId;
			}
			// get user's pokemon list to be displayed
			model.addAttribute("pokemonlist", userService.findUserById(longId).getPokemonRoster());
			model.addAttribute("user", userService.findUserById(currentUserId));
			return "pokedex/MyPokemon.jsp";
		}
	}
	// Show other users
	@RequestMapping("/show/users")
	public String showAllUsers(Model model, HttpSession session) {
		// get user from session, save them in the model and return requested page
		// if no user, return to login
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "redirect:/";
		// else get users and add to model
		} else {
			List<User> userList= userService.getAllUsers();
			model.addAttribute("userList", userList);
			model.addAttribute("user", userService.findUserById(currentUserId));
			return "pokedex/ViewUsers.jsp";
		}
	}	
}
