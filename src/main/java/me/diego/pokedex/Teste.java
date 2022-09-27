package me.diego.pokedex;

import me.diego.pokedex.service.PokeTypeService;
import me.diego.pokedex.service.pokeapi.PokemonApiModel;
import me.diego.pokedex.utils.PokeConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Teste {
    public static void main(String[] args) {
        System.out.println(getListOfPokemon(10));
        System.out.println(getListOfPokemon(10).size());
    }

    public static List<PokemonApiModel> getListOfPokemon(final int quantity) {
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
