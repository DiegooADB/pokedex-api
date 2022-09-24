package me.diego.pokedex.service.pokeapi;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PokeApiService {

    public PokemonApiModel findPokemonById(Long id) {
        String resourceUrl = "https://pokeapi.co/api/v2/pokemon/" + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PokemonApiModel> responseEntity = restTemplate.getForEntity(resourceUrl, PokemonApiModel.class);
        return responseEntity.getBody();
    }

}
