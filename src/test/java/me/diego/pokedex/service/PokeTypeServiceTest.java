package me.diego.pokedex.service;

import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.model.PokeTypeModel;
import me.diego.pokedex.repository.PokeTypeRepository;
import me.diego.pokedex.util.PokeTypeModelCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PokeTypeServiceTest {

    @InjectMocks
    private PokeTypeService pokeTypeService;

    @Mock
    private PokeTypeRepository pokeTypeRepository;

    @BeforeEach
    void setUp() {
        BDDMockito.when(pokeTypeRepository.findByPokeTypeString(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(PokeTypeModelCreator.createValidWaterPokeType()));
    }

    @Test
    @DisplayName("findByPokeTypeString returns pokeType when successful")
    void findByPokeTypeString_ReturnsPokeType_WhenSuccessful() {
        PokeTypeModel pokeTypeFound = pokeTypeService.findByPokeTypeString("WATER");

        PokeTypeModel expectedPokeType = PokeTypeModelCreator.createValidWaterPokeType();

        Assertions.assertThat(pokeTypeFound)
                .isNotNull();

        Assertions.assertThat(pokeTypeFound.getPokeType())
                .isEqualTo(expectedPokeType.getPokeType());
    }

    @Test
    @DisplayName("findByPokeTypeString throws BadRequestException when not found")
    void findByPokeTypeString_ThrowsBadRequestException_WhenNotFound() {
        BDDMockito.when(pokeTypeRepository.findByPokeTypeString(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> pokeTypeService.findByPokeTypeString("invalidPokeType"));
    }
}