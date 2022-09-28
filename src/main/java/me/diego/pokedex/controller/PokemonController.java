package me.diego.pokedex.controller;

import me.diego.pokedex.model.Pokemon;
import me.diego.pokedex.model.Trainer;
import me.diego.pokedex.model.dto.PokemonPopulateDTO;
import me.diego.pokedex.model.dto.PokemonPostDTO;
import me.diego.pokedex.service.PokemonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
    public ResponseEntity<Pokemon> registerPokemon(@Valid @RequestBody PokemonPostDTO pokemonPost) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pokemonService.savePokemon(pokemonPost));
    }

    @PostMapping(path = "/capture")
    public ResponseEntity<Trainer> capturePokemon(@Valid @RequestBody PokemonPostDTO pokemonDto, @RequestHeader Map<String, String> headers) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pokemonService.capturePokemon(pokemonDto, headers));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "/populate")
    public ResponseEntity<List<Pokemon>> populateDbWithPokemon(@Valid @RequestBody PokemonPopulateDTO pokemonPopulateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pokemonService.populateDb(pokemonPopulateDTO));
    }

    @GetMapping(path = "/available")
    public ResponseEntity<Page<Pokemon>> getAvailablePokemon(@RequestParam(required = false) String pokeType, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.FOUND).body(pokemonService.listAllAvailablePokemonByType(pokeType, pageable));
    }
}
