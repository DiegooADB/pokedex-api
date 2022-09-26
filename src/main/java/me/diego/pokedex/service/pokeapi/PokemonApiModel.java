package me.diego.pokedex.service.pokeapi;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PokemonApiModel {
    private long id;
    private String name;
    private int base_experience;
    private int height;
    private boolean is_default;
    private int order;
    private int weight;
    private Object abilities;
    private Object forms;
    private Object game_indices;
    private Object held_items;
    private String location_area_encounters;
    private Object moves;
    private Map<String, Object> sprites;
    private Object species;
    private Object stats;
    private List<Map<String, Object>> types;

}
