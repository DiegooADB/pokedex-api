package me.diego.pokedex.service;

import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.model.Pokemon;
import me.diego.pokedex.model.Trainer;
import me.diego.pokedex.model.UserModel;
import me.diego.pokedex.model.dto.PokemonPostDto;
import me.diego.pokedex.repository.PokemonRepository;
import me.diego.pokedex.service.pokeapi.PokeApiService;
import me.diego.pokedex.utils.PokeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokemonService {

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    PokeApiService pokeApiService;

    @Autowired
    PokeConverter pokeConverter;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public Trainer capturePokemon(PokemonPostDto pokemon, UserDetails user) {
        Pokemon pokemonFound = findPokemonById(pokemon.getId());
        UserModel userModel = userDetailsService.loadUserModelByUsername(user.getUsername());
        Trainer trainer = userModel.getTrainer();

        List<Pokemon> pokemons = trainer.getPokemons();
        pokemons.add(pokemonFound);

        trainer.setPokemons(pokemons);

        return trainer;
    }

    public Pokemon savePokemon(long id) {
        Pokemon pokemon = pokeConverter.toPokemonEntity(pokeApiService.findPokemonById(id));

        return pokemonRepository.save(pokemon);
    }

    public Pokemon findPokemonById(long id) {
        return pokemonRepository.findById(id).orElseThrow(() -> {
            throw new BadRequestException("Pokemon not found");
        });
    }
}
