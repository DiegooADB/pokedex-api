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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final PokeApiService pokeApiService;
    private final PokeConverter pokeConverter;
    private final UserDetailsServiceImpl userDetailsService;
    private final TrainerService trainerService;

    public PokemonService(PokemonRepository pokemonRepository, PokeApiService pokeApiService, PokeConverter pokeConverter, UserDetailsServiceImpl userDetailsService, TrainerService trainerService) {
        this.pokemonRepository = pokemonRepository;
        this.pokeApiService = pokeApiService;
        this.pokeConverter = pokeConverter;
        this.userDetailsService = userDetailsService;
        this.trainerService = trainerService;
    }

    @Transactional
    public Trainer capturePokemon(PokemonPostDto pokemon, UserDetails user) {
        Pokemon pokemonFound = findPokemonById(pokemon.getId());
        if (pokemonFound.isCaptured()) {
            throw new ConflictException("Pokemon is already captured", "pokemon");
        }

        UserModel userModel = userDetailsService.loadUserModelByUsername(user.getUsername());
        Trainer trainer = userModel.getTrainer();

        if (trainer == null) {
            throw new BadRequestException("You cannot capture a pokemon without create a trainer");
        }

        List<Pokemon> pokemons = trainer.getPokemons();
        pokemons.add(pokemonFound);

        pokemonFound.setCaptured(true);
        this.updatePokemon(pokemonFound);

        trainer.setPokemons(pokemons);
        return trainerService.updateTrainerPokemon(trainer);
    }

    @Transactional
    public Pokemon savePokemon(PokemonPostDto pokemonPostDto) {
        Pokemon pokemon = pokeConverter.toPokemonEntity(pokeApiService.findPokemonById(pokemonPostDto.getId()));

        return pokemonRepository.save(pokemon);
    }

    public Pokemon findPokemonById(long id) {
        return pokemonRepository.findById(id).orElseThrow(() -> {
            throw new BadRequestException("Pokemon not found");
        });
    }

    @Transactional
    public void updatePokemon(Pokemon pokemon) {
        pokemonRepository.save(pokemon);
    }
}
