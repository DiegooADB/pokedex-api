package me.diego.pokedex.util;

import me.diego.pokedex.model.Trainer;
import me.diego.pokedex.model.dto.TrainerPostDTO;

import java.util.ArrayList;
import java.util.List;

public class TrainerCreator {
    public static Trainer createValidTrainer() {
        return Trainer.builder()
                .age(1)
                .name("TrainerTest")
                .region(RegionCreator.createValidKantoRegion())
                .pokemons(List.of(PokemonCreator.createValidPokemonNotCaptured()))
                .build();
    }

    public static Trainer createTrainerToBeSaved() {
        return Trainer.builder()
                .name("TrainerTest")
                .region(RegionCreator.createValidKantoRegion())
                .pokemons(List.of(PokemonCreator.createValidPokemonNotCaptured()))
                .build();
    }

    public static Trainer createValidTrainerToBeUpdated() {
        return Trainer.builder()
                .name("TrainerTest")
                .region(RegionCreator.createValidKantoRegion())
                .pokemons(new ArrayList<>(List.of(PokemonCreator.createValidPokemonCaptured())))
                .build();
    }

    public static Trainer createValidTrainerUpdated() {
        return Trainer.builder()
                .name("TrainerTest")
                .region(RegionCreator.createValidKantoRegion())
                .pokemons(new ArrayList<>(
                        List.of(PokemonCreator.createValidPokemonCaptured(), PokemonCreator.createAnotherValidPokemonCaptured())
                ))
                .build();
    }

    public static TrainerPostDTO createValidTrainerPostDto() {
        return TrainerPostDTO.builder()
                .name("TrainerTest")
                .age(1)
                .regionName("kanto")
                .build();
    }
}
