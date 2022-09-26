package me.diego.pokedex.service;

import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.model.PokeTypeModel;
import me.diego.pokedex.repository.PokeTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class PokeTypeService {
    private final PokeTypeRepository pokeTypeRepository;

    public PokeTypeService(PokeTypeRepository pokeTypeRepository) {
        this.pokeTypeRepository = pokeTypeRepository;
    }

    public PokeTypeModel findByPokeTypeString(String poketype) {
        return pokeTypeRepository.findByPokeTypeString(poketype.toUpperCase())
                .orElseThrow(() -> new BadRequestException("Poke type not found"));
    }
}
