package me.diego.pokedex.utils;

import me.diego.pokedex.model.PokeTypeModel;
import me.diego.pokedex.model.Pokemon;
import me.diego.pokedex.service.PokeTypeService;
import me.diego.pokedex.service.pokeapi.PokemonApiModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PokeConverter {
    private final PokeTypeService pokeTypeService;

    public PokeConverter(PokeTypeService pokeTypeService) {
        this.pokeTypeService = pokeTypeService;
    }


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

    public List<Pokemon> toPokemonEntityList(List<PokemonApiModel> pokemonApiModels) {
        return pokemonApiModels.stream().map(pokemonApiModel -> {
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
        }).toList();
    }
}
