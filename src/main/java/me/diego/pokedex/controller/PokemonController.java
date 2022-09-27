package me.diego.pokedex.controller;

import me.diego.pokedex.model.Pokemon;
import me.diego.pokedex.model.Trainer;
import me.diego.pokedex.model.dto.PokemonPostDto;
import me.diego.pokedex.service.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/pokemon")
public class PokemonController {
    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "/register")
    public ResponseEntity<Pokemon> registerPokemon(@Valid @RequestBody PokemonPostDto pokemonPost) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pokemonService.savePokemon(pokemonPost));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(path = "/capture")
    public ResponseEntity<Trainer> capturePokemon(@Valid @RequestBody PokemonPostDto pokemonDto, @RequestHeader Map<String, String> headers) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pokemonService.capturePokemon(pokemonDto, headers));
    }
}
