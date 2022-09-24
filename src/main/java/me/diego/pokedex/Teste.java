package me.diego.pokedex;

import me.diego.pokedex.model.Pokemon;
import me.diego.pokedex.service.pokeapi.PokemonApiModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class Teste {
    public static void main(String[] args) {
    }

    public static List<Pokemon> findPokemons() {
        String resourceUrl = "https://pokeapi.co/api/v2/pokemon?limit=50";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(resourceUrl, Map.class);
        List<Pokemon> pokemons = (List) responseEntity.getBody().get("results");
        return pokemons;
    }

    public static PokemonApiModel findPokemonById(Long id) {
        String resourceUrl = "https://pokeapi.co/api/v2/pokemon/" + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PokemonApiModel> responseEntity = restTemplate.getForEntity(resourceUrl, PokemonApiModel.class);
        return responseEntity.getBody();
    }
}
