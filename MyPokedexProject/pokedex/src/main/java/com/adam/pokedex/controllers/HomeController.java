package com.adam.pokedex.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.adam.pokedex.models.*;
import com.adam.pokedex.services.*;
import com.mashape.unirest.http.exceptions.UnirestException;

@Controller
public class HomeController {
	@Autowired
	private UserService userService;
	@Autowired
	private PokemonService pokeService;

// REGISTER User
	@RequestMapping("/")
	public String registerForm(@ModelAttribute("user") User user) {
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
			//model.addAttribute("user", userService.findUserById(currentUserId));
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
			//model.addAttribute("user", userService.findUserById(currentUserId));
			pokeService.addSearchPokemonInfoToModelFromSession(model, session);
			return "pokedex/addPokemon.jsp";
		}
	}
	@PostMapping("/addpokemonwithname")
	public String addPokemonWithName(
			@NotBlank @RequestParam("name") String name,
			Model model,
			HttpSession session) {
		// if no user, return to login
		Long currentUserId = (Long) session.getAttribute("userId");
		if (currentUserId == null) {
			return "redirect:/";
		} else if (name.isBlank()) {
			model.addAttribute("error","You haven't given me a name for your pokemon!");
			return "pokedex/addPokemon.jsp";
		} else {
			pokeService.createPokemonWithName(name, session);
			return "redirect:/addpokemon";
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
			return "redirect:/addpokemon";
		}
	}

// DISPLAY Functionality
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
			if(userService.findUserById(longId)==null) {
				longId=currentUserId;
				return "redirect:/show/mypokedex/"+longId;
			}
			// get user's pokemon list to be displayed
			model.addAttribute("pokemonlist", userService.findUserById(longId).getPokemonRoster());
			return "pokedex/index.jsp";
		}
	}
}
