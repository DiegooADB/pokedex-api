package me.diego.pokedex.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PokemonPostDto {
    @NotNull(message = "Poke id is required")
    private long pokeId;
}
