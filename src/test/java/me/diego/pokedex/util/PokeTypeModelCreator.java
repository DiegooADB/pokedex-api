package me.diego.pokedex.util;

import me.diego.pokedex.enums.PokeType;
import me.diego.pokedex.model.PokeTypeModel;

public class PokeTypeModelCreator {
    public static PokeTypeModel createValidWaterPokeType() {
        return PokeTypeModel.builder()
                .id(1L)
                .pokeType(PokeType.WATER)
                .build();
    }

    public static PokeTypeModel createValidGroundPokeType() {
        return PokeTypeModel.builder()
                .id(2L)
                .pokeType(PokeType.GROUND)
                .build();
    }

    public static PokeTypeModel createValidSteelPokeType() {
        return PokeTypeModel.builder()
                .id(3L)
                .pokeType(PokeType.STEEL)
                .build();
    }
}
