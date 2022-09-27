package me.diego.pokedex.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class PokemonPopulateDTO {
    @Min(value = 1, message = "Quantity cannot be less than 0")
    @Max(value = 905, message = "Quantity cannot be greater than 905")
    private int quantity;
}
