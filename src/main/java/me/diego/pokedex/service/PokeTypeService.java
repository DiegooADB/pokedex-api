package me.diego.pokedex.service;

import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.model.PokeTypeModel;
import me.diego.pokedex.repository.PokeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PokeTypeService {
    @Autowired
    PokeTypeRepository pokeTypeRepository;

    public PokeTypeModel findByPokeTypeString(String poketype) {
        return pokeTypeRepository.findByPokeTypeString(poketype.toUpperCase())
                .orElseThrow(() -> new BadRequestException("Poke type not found"));
    }
}
