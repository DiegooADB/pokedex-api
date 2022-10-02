package me.diego.pokedex.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.ui.Model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class TrainerPostDTO {
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Region cannot be empty")
    private String regionName;
    @Min(value = 1, message = "Age cannot be less than 0")
    private int age;

    public TrainerPostDTO(String name, String regionName, int age) {
        this.name = name;
        this.regionName = regionName;
        this.age = age;
    }

    public TrainerPostDTO() {
    }
}
