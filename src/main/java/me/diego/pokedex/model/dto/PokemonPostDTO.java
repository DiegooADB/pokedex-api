package me.diego.pokedex.model.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PokemonPostDTO {
    @Min(value = 1, message = "Id cannot be less than 0")
    private long pokeId;
}
