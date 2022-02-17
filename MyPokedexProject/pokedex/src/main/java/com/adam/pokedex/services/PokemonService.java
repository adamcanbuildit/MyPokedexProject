package com.adam.pokedex.services;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.adam.pokedex.models.*;
import com.adam.pokedex.repositories.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class PokemonService {
	private final UserRepository userRepo;
	private final PokemonRepository pokeRepo;
	String apiUrl = "https://pokeapi.co/api/v2/pokemon/";

	public PokemonService(UserRepository userRepo, PokemonRepository pokeRepo) {
		this.userRepo = userRepo;
		this.pokeRepo = pokeRepo;
	}
	
	// Create a pokemon entry in the database
	@SuppressWarnings("unchecked")
	public void createPokemonWithName(String name, HttpSession session) {
		HashMap<String,Long> stats = (HashMap<String, Long>) session.getAttribute("activePokemonStats");
		ArrayList<String> types = (ArrayList<String>) session.getAttribute("activePokemonTypes");
		Long userId = (Long) session.getAttribute("userId");
		User user = userRepo.findById(userId).get();
		Pokemon pokemon = new Pokemon(
				name,
				(Long) session.getAttribute("activePokemonId"),
				(String) session.getAttribute("activePokemonSpecies"),
				user,
				stats.get("hp"),
				stats.get("spd"),
				stats.get("atk"),
				stats.get("def"),
				stats.get("sp_atk"),
				stats.get("sp_def"),
				types,
				(String) session.getAttribute("activePokemonImageUrl"));
		pokeRepo.save(pokemon);
	}
	// Creates entry with species as name
	@SuppressWarnings("unchecked")
	public void createPokemonWithoutName(HttpSession session) {
		HashMap<String,Long> stats = (HashMap<String, Long>) session.getAttribute("activePokemonStats");
		ArrayList<String> types = (ArrayList<String>) session.getAttribute("activePokemonTypes");
		Long userId = (Long) session.getAttribute("userId");
		User user = userRepo.findById(userId).get();
		Pokemon pokemon = new Pokemon(
				(Long) session.getAttribute("activePokemonId"),
				(String) session.getAttribute("activePokemonSpecies"),
				user,
				stats.get("hp"),
				stats.get("spd"),
				stats.get("atk"),
				stats.get("def"),
				stats.get("sp_atk"),
				stats.get("sp_def"),
				types,
				(String) session.getAttribute("activePokemonImageUrl"));
		pokeRepo.save(pokemon);
	}

	// update Pokemon with new name
	public void updatePokemonName(Pokemon pokemon,String name) {
		pokemon.setName(name);
		pokeRepo.save(pokemon);
	}
	
//  --  Function to grab a pokemon from the web and grab the info we need
//  --  Then store that info into the session as activePokemon
	public void findPokemonFromWebAndAddToSession(String query, Model model, HttpSession session) {
		// Get response from API
		HttpResponse<JsonNode> searchResult = null;
//		searchResult = Unirest.get(apiUrl + query).asJson();
		try {
			searchResult = Unirest.get(apiUrl + query).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
		// Re-cast the search results into a JSONObject
		JSONObject resultObject = searchResult.getBody().getObject();

		// Grab the pokemon id# and name from our results
		Long searchPokemonId = resultObject.getLong("id");
		String searchPokemonSpecies = resultObject.getString("name");

		// Grab the pokemon's image
		String pokemonImageUrl = resultObject.getJSONObject("sprites").getString("front_default");

		// Grab pokemon stats and put in hashmap
		HashMap<String, Long> searchPokemonStats = new HashMap<String, Long>();
		searchPokemonStats.put("hp", resultObject.getJSONArray("stats").getJSONObject(0).getLong("base_stat"));
		searchPokemonStats.put("atk", resultObject.getJSONArray("stats").getJSONObject(1).getLong("base_stat"));
		searchPokemonStats.put("def", resultObject.getJSONArray("stats").getJSONObject(2).getLong("base_stat"));
		searchPokemonStats.put("sp_atk", resultObject.getJSONArray("stats").getJSONObject(3).getLong("base_stat"));
		searchPokemonStats.put("sp_def", resultObject.getJSONArray("stats").getJSONObject(4).getLong("base_stat"));
		searchPokemonStats.put("spd", resultObject.getJSONArray("stats").getJSONObject(5).getLong("base_stat"));

		// Get the types of our pokemon and put in array
		ArrayList<String> searchPokemonTypes = new ArrayList<String>();
		JSONArray jArrayTypes = resultObject.getJSONArray("types");
		ArrayList<JSONObject> searchPokemonTypesList = new ArrayList<JSONObject>();
		// Iterate over each of the pokemon's type and get the type name
		for (int i=0;i<jArrayTypes.length();i++) {
			searchPokemonTypesList.add(jArrayTypes.getJSONObject(i));
		}
		for (JSONObject jObj : searchPokemonTypesList) {
			searchPokemonTypes.add(jObj.getJSONObject("type").getString("name"));
		}
		session.setAttribute("activePokemonId", searchPokemonId);
		session.setAttribute("activePokemonSpecies", searchPokemonSpecies);
		session.setAttribute("activePokemonImageUrl", pokemonImageUrl);
		session.setAttribute("activePokemonStats", searchPokemonStats);
		session.setAttribute("activePokemonTypes", searchPokemonTypes);
	}
// - Add search pokemon data to the jsp model
	@SuppressWarnings("unchecked")
	public void addSearchPokemonInfoToModelFromSession(Model model, HttpSession session) {
		model.addAttribute("pokemonId", (Long) session.getAttribute("activePokemonId"));
		model.addAttribute("pokemonSpecies", (String) session.getAttribute("activePokemonSpecies"));
		model.addAttribute("pokemonUrl", (String) session.getAttribute("activePokemonImageUrl"));
		model.addAttribute("pokemonStats", (HashMap<String, Long>) session.getAttribute("activePokemonStats"));
		model.addAttribute("pokemonType", (ArrayList<String>) session.getAttribute("activePokemonTypes"));
	}
// - Remove active search pokemon from the session
	public void removeSearchPokemonFromSession(HttpSession session) {
		session.removeAttribute("pokemonId");
		session.removeAttribute("pokemonSpecies");
		session.removeAttribute("pokemonUrl");
		session.removeAttribute("pokemonStats");
		session.removeAttribute("pokemonType");
	}
}