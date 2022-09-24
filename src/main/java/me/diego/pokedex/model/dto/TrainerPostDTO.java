package me.diego.pokedex.model.dto;

import lombok.Data;
import org.springframework.ui.Model;

@Data
public class TrainerPostDTO {
    private String name;
    private String regionName;
    private int age;
}
