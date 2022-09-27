package me.diego.pokedex.service;

import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.exception.ConflictException;
import me.diego.pokedex.model.Pokemon;
import me.diego.pokedex.model.Trainer;
import me.diego.pokedex.model.UserModel;
import me.diego.pokedex.model.dto.PokemonPopulateDTO;
import me.diego.pokedex.model.dto.PokemonPostDTO;
import me.diego.pokedex.repository.PokemonRepository;
import me.diego.pokedex.service.pokeapi.PokeApiService;
import me.diego.pokedex.utils.PokeConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final PokeApiService pokeApiService;
    private final PokeConverter pokeConverter;
    private final UserDetailsServiceImpl userDetailsService;
    private final TrainerService trainerService;
    private final AuthService authService;

    public PokemonService(PokemonRepository pokemonRepository, PokeApiService pokeApiService, PokeConverter pokeConverter, UserDetailsServiceImpl userDetailsService, TrainerService trainerService, AuthService authService) {
        this.pokemonRepository = pokemonRepository;
        this.pokeApiService = pokeApiService;
        this.pokeConverter = pokeConverter;
        this.userDetailsService = userDetailsService;
        this.trainerService = trainerService;
        this.authService = authService;
    }

    @Transactional
    public Trainer capturePokemon(PokemonPostDTO pokemon, Map<String, String> headers) {
        Pokemon pokemonFound = findPokemonById(pokemon.getPokeId());
        if (pokemonFound.isCaptured()) {
            throw new ConflictException("Pokemon is already captured");
        }

        String username = authService.getUsernameByJwt(headers);
        UserModel userModel = userDetailsService.loadUserModelByUsername(username);
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
    public Pokemon savePokemon(PokemonPostDTO pokemonPostDto) {
        Pokemon pokemon = pokeConverter.toPokemonEntity(pokeApiService.findPokemonById(pokemonPostDto.getPokeId()));

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

    public List<Pokemon> populateDb(PokemonPopulateDTO pokemonPopulateDTO) {
        return pokeConverter.toPokemonEntityList(pokeApiService.getListOfPokemon(pokemonPopulateDTO.getQuantity())).stream()
                .map(pokemonRepository::save).toList();
    }

    public Page<Pokemon> listAllAvailablePokemon(Pageable pageable) {
        return pokemonRepository.findByCapturedFalse(pageable);
    }
}
