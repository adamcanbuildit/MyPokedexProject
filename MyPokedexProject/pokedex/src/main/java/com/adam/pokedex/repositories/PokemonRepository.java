package com.adam.pokedex.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.adam.pokedex.models.Pokemon;

public interface PokemonRepository extends CrudRepository<Pokemon, Long> {

	List<Pokemon> findAll();
}
