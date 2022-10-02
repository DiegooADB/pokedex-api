package me.diego.pokedex.util;

import me.diego.pokedex.enums.RegionName;
import me.diego.pokedex.model.Region;

public class RegionCreator {
    public static Region createValidKantoRegion() {
        return Region.builder()
                .id(1L)
                .regionName(RegionName.KANTO)
                .build();
    }
}
