package me.diego.pokedex.controller;

import me.diego.pokedex.model.Pokemon;
import me.diego.pokedex.model.Trainer;
import me.diego.pokedex.model.UserModel;
import me.diego.pokedex.model.dto.PokemonPostDto;
import me.diego.pokedex.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/pokemon")
public class PokemonController {

    @Autowired
    PokemonService pokemonService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "/register/{pokeId}")
    public ResponseEntity<Pokemon> registerPokemon(@PathVariable long pokeId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pokemonService.savePokemon(pokeId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(path = "/capture")
    public ResponseEntity<Trainer> capturePokemon(@RequestBody PokemonPostDto pokemonDto, Authentication authentication) {
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED).body(pokemonService.capturePokemon(pokemonDto, principal));
    }
}
