package me.diego.pokedex.utils;

import me.diego.pokedex.model.PokeTypeModel;
import me.diego.pokedex.model.Pokemon;
import me.diego.pokedex.service.PokeTypeService;
import me.diego.pokedex.service.pokeapi.PokemonApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PokeConverter {
    @Autowired
    PokeTypeService pokeTypeService;

    public Pokemon toPokemonEntity(PokemonApiModel pokemonApiModel) {
        String imageURL = (String) pokemonApiModel.getSprites().get("front_default");
        List<PokeTypeModel> attributes = pokemonApiModel.getTypes().stream().map(map -> {
            Map<String, Object> type = (Map<String, Object>) map.get("type");
            String pokeTypeName = (String) type.get("name");
            return pokeTypeService.findByPokeTypeString(pokeTypeName);
        }).toList();

        return Pokemon.builder()
                .pokeName(pokemonApiModel.getName())
                .imageUrl(imageURL)
                .attributes(attributes)
                .build();
    }
}