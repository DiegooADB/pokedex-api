package me.diego.pokedex.util;

import me.diego.pokedex.model.Pokemon;

import java.util.List;

public class PokemonCreator {
    public static Pokemon createValidPokemonNotCaptured() {
        return Pokemon.builder()
                .pokeId(1L)
                .pokeName("sobble")
                .imageUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/816.png")
                .attributes(List.of(PokeTypeModelCreator.createValidWaterPokeType()))
                .captured(false)
                .build();
    }

    public static Pokemon createValidPokemonCaptured() {
        return Pokemon.builder()
                .pokeId(1L)
                .pokeName("donphan")
                .imageUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/232.png")
                .attributes(List.of(PokeTypeModelCreator.createValidGroundPokeType()))
                .captured(true)
                .build();
    }

    public static Pokemon createAnotherValidPokemonCaptured() {
        return Pokemon.builder()
                .pokeId(2L)
                .pokeName("klink")
                .imageUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/599.png")
                .attributes(List.of(PokeTypeModelCreator.createValidSteelPokeType()))
                .captured(true)
                .build();
    }
}
