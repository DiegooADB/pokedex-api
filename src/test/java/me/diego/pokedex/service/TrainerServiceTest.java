package me.diego.pokedex.service;

import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.exception.ConflictException;
import me.diego.pokedex.model.Pokemon;
import me.diego.pokedex.model.Region;
import me.diego.pokedex.model.Trainer;
import me.diego.pokedex.model.dto.TrainerPostDTO;
import me.diego.pokedex.repository.TrainerRepository;
import me.diego.pokedex.util.PokemonCreator;
import me.diego.pokedex.util.RegionCreator;
import me.diego.pokedex.util.TrainerCreator;
import me.diego.pokedex.util.UserModelCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class TrainerServiceTest {

    @InjectMocks
    private TrainerService trainerService;

    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private RegionService regionService;
    @Mock
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        PageImpl<Trainer> trainerPage = new PageImpl<>(List.of(TrainerCreator.createValidTrainer()));
        BDDMockito.when(trainerRepository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(trainerPage);

        BDDMockito.when(trainerRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(TrainerCreator.createValidTrainer()));

        BDDMockito.when(trainerRepository.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(TrainerCreator.createValidTrainer()));

        BDDMockito.when(trainerRepository.save(ArgumentMatchers.any(Trainer.class)))
                .thenReturn(TrainerCreator.createValidTrainer());

        BDDMockito.when(userDetailsService.loadUserModelByUsername(ArgumentMatchers.anyString()))
                .thenReturn(UserModelCreator.createValidUser());

        BDDMockito.when(authService.getUsernameByJwt(ArgumentMatchers.anyMap()))
                .thenReturn("UserTest");

        BDDMockito.when(regionService.findByRegionNameString(ArgumentMatchers.anyString()))
                .thenReturn(RegionCreator.createValidKantoRegion());

        BDDMockito.when(trainerRepository.findByRegion(ArgumentMatchers.any(Region.class), ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(trainerPage);
    }

    @Test
    @DisplayName("getAllTrainers returns pages from trainer when successful ")
    void getAllTrainers_ReturnsListOfTrainerInsideAPageObject_WhenSuccessful() {
        Trainer trainerExpected = TrainerCreator.createValidTrainer();

        Page<Trainer> trainerPage = trainerService.getAllTrainers(PageRequest.of(1, 1));

        Assertions.assertThat(trainerPage)
                .isNotNull();

        Assertions.assertThat(trainerPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Trainer trainerResponse = trainerPage.toList().get(0);

        Assertions.assertThat(trainerResponse.getName())
                .isEqualTo(trainerExpected.getName());

        Assertions.assertThat(trainerResponse.getPokemons().get(0).getPokeName())
                .isEqualTo(trainerExpected.getPokemons().get(0).getPokeName());
    }

    @Test
    @DisplayName("findById returns trainer when successful")
    void findById_ReturnsTrainer_WhenSuccessful() {
        Trainer trainerExpected = TrainerCreator.createValidTrainer();

        Trainer trainerFound = trainerService.findById(1L);

        Assertions.assertThat(trainerFound)
                .isNotNull();

        Assertions.assertThat(trainerFound.getId())
                .isEqualTo(trainerExpected.getId());

        Assertions.assertThat(trainerFound.getPokemons().get(0).getPokeId())
                .isEqualTo(trainerExpected.getPokemons().get(0).getPokeId());
    }

    @Test
    @DisplayName("findById throws BadRequest when trainer not found")
    void findById_ThrowsBadRequest_WhenTrainerNotFound() {
        BDDMockito.when(trainerRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> trainerService.findById(1L));
    }

    @Test
    @DisplayName("findByName return list of trainer when successful")
    void findByName_ReturnsListOfTrainer_whenSuccessful() {
        Trainer trainerExpected = TrainerCreator.createValidTrainer();

        List<Trainer> trainerFound = trainerService.findByName("TrainerTest");

        Assertions.assertThat(trainerFound)
                .isNotEmpty()
                .hasSize(1)
                .isNotNull();

        Assertions.assertThat(trainerFound.get(0).getId())
                .isEqualTo(trainerExpected.getId());

        Assertions.assertThat(trainerFound.get(0).getPokemons().get(0).getPokeId())
                .isEqualTo(trainerExpected.getPokemons().get(0).getPokeId());
    }

    @Test
    @DisplayName("saveTrainer return trainer when successful")
    void saveTrainer_returnsTrainer_whenSuccessful() {
        BDDMockito.when(trainerRepository.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of());

        Trainer trainerExpected = TrainerCreator.createValidTrainer();

        Trainer trainerSaved = trainerService.saveTrainer(TrainerCreator.createValidTrainerPostDto(), Map.of("authorization", "Bearer asdawdasd"));

        Assertions.assertThat(trainerSaved)
                .isNotNull();

        Assertions.assertThat(trainerSaved.getId())
                .isEqualTo(trainerExpected.getId());

        Assertions.assertThat(trainerSaved.getRegion().getRegionName())
                .isEqualTo(trainerExpected.getRegion().getRegionName());

        Assertions.assertThat(trainerSaved.getPokemons().get(0).getPokeName())
                .isEqualTo(trainerExpected.getPokemons().get(0).getPokeName());
    }

    @Test
    @DisplayName("saveTrainer throws ConflictException when trainer already exists")
    void saveTrainer_throwsConflictException_WhenTrainerAlreadyExists() {
        Assertions.assertThatExceptionOfType(ConflictException.class)
                .isThrownBy(() -> trainerService
                        .saveTrainer(TrainerCreator.createValidTrainerPostDto(), Map.of("authorization", "Bearer asdawd")));
    }

    @Test
    @DisplayName("updateTrainerPokemon returns trainer when successful")
    void updateTrainerPokemon_ReturnsTrainer_WhenSuccessful() {
        BDDMockito.when(trainerRepository.save(ArgumentMatchers.any(Trainer.class)))
                .thenReturn(TrainerCreator.createValidTrainerUpdated());

        Trainer trainerExpected = TrainerCreator.createValidTrainerUpdated();

        Trainer trainerToBeUpdated = TrainerCreator.createValidTrainerToBeUpdated();

        List<Pokemon> pokemons = trainerToBeUpdated.getPokemons();
        pokemons.add(PokemonCreator.createAnotherValidPokemonCaptured());

        trainerToBeUpdated.setPokemons(pokemons);
        Trainer trainerUpdated = trainerService.updateTrainerPokemon(trainerToBeUpdated);

        Assertions.assertThat(trainerUpdated)
                .isNotNull();

        Assertions.assertThat(trainerUpdated.getPokemons())
                .isNotEmpty()
                .hasSize(2)
                .isNotNull();

        Assertions.assertThat(trainerUpdated.getName())
                .isEqualTo(trainerExpected.getName());

        Assertions.assertThat(trainerUpdated.getPokemons().get(0).getPokeName())
                .isEqualTo(PokemonCreator.createValidPokemonCaptured().getPokeName());

        trainerUpdated.getPokemons().forEach(pokemon -> {
            Assertions.assertThat(pokemon.isCaptured()).isTrue();
        });
    }

    @Test
    @DisplayName("getTrainerByRegion returns pages from trainer when successful")
    void getTrainerByRegion_ReturnsPagesFromTrainer_WhenSuccessful() {
        Trainer trainerExpected = TrainerCreator.createValidTrainer();

        Page<Trainer> trainerPage = trainerService.getTrainerByRegion("Kanto", PageRequest.of(1, 1));

        Assertions.assertThat(trainerPage)
                .isNotNull();

        Assertions.assertThat(trainerPage.toList())
                .isNotEmpty()
                .hasSize(1)
                .isNotNull();

        Assertions.assertThat(trainerPage.toList().get(0).getRegion().getRegionName())
                .isEqualTo(trainerExpected.getRegion().getRegionName());
    }
}