package me.diego.pokedex.service;

import me.diego.pokedex.enums.RoleName;
import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.model.Region;
import me.diego.pokedex.repository.RegionRepository;
import me.diego.pokedex.util.RegionCreator;
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

@ExtendWith(SpringExtension.class)
class RegionServiceTest {

    @InjectMocks
    private RegionService regionService;

    @Mock
    private RegionRepository regionRepository;

    @BeforeEach
    void setUp() {
        BDDMockito.when(regionRepository.findByRegionNameString(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(RegionCreator.createValidKantoRegion()));
    }

    @Test
    @DisplayName("findByRegionNameString returns region when successful")
    void findByRegionNameString_ReturnsRegion_WhenSuccessful() {
        Region regionFound = regionService.findByRegionNameString("Kanto");

        Region expectedRegion = RegionCreator.createValidKantoRegion();

        Assertions.assertThat(regionFound)
                .isNotNull();

        Assertions.assertThat(expectedRegion.getRegionName())
                .isEqualTo(expectedRegion.getRegionName());

    }

    @Test
    @DisplayName("findByRegionNameString throws BadRequestException when not found")
    void findByRegionNameString_ThrowsBadRequestException_WhenNotFound() {
        BDDMockito.when(regionRepository.findByRegionNameString(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> regionService.findByRegionNameString("InvalidRegion"));
    }

}