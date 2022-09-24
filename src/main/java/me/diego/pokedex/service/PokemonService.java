package me.diego.pokedex.service;

import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.exception.ConflictException;
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

    @Autowired
    TrainerService trainerService;

    public Trainer capturePokemon(PokemonPostDto pokemon, UserDetails user) {
        Pokemon pokemonFound = findPokemonById(pokemon.getId());
        if (pokemonFound.isCaptured()) {
            throw new ConflictException("Pokemon is already captured", "pokemon");
        }

        UserModel userModel = userDetailsService.loadUserModelByUsername(user.getUsername());
        Trainer trainer = userModel.getTrainer();

        if (trainer == null ) {
            throw new BadRequestException("You cannot capture a pokemon without create a trainer");
        }

        List<Pokemon> pokemons = trainer.getPokemons();
        pokemons.add(pokemonFound);

        pokemonFound.setCaptured(true);
        this.updatePokemon(pokemonFound);

        trainer.setPokemons(pokemons);
        return trainerService.updateTrainerPokemon(trainer);
    }

    public Pokemon savePokemon(PokemonPostDto pokemonPostDto) {
        Pokemon pokemon = pokeConverter.toPokemonEntity(pokeApiService.findPokemonById(pokemonPostDto.getId()));

        return pokemonRepository.save(pokemon);
    }

    public Pokemon findPokemonById(long id) {
        return pokemonRepository.findById(id).orElseThrow(() -> {
            throw new BadRequestException("Pokemon not found");
        });
    }

    public void updatePokemon(Pokemon pokemon) {
        pokemonRepository.save(pokemon);
    }
}
