package me.diego.pokedex.service.pokeapi;

import me.diego.pokedex.utils.PokeConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PokeApiService {
    public PokemonApiModel findPokemonById(Long id) {
        String resourceUrl = "https://pokeapi.co/api/v2/pokemon/" + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PokemonApiModel> responseEntity = restTemplate.getForEntity(resourceUrl, PokemonApiModel.class);
        return responseEntity.getBody();
    }

    public List<PokemonApiModel> getListOfPokemon(final int quantity) {
        return ThreadLocalRandom.current().ints(quantity, 1, 905)
                .mapToObj(num -> {
                    return "https://pokeapi.co/api/v2/pokemon/" + num;
                })
                .map(url -> {
                    RestTemplate restTemplate = new RestTemplate();
                    return restTemplate.getForEntity(url, PokemonApiModel.class).getBody();
                }).toList();
    }
}
