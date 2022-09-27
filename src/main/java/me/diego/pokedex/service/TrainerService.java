package me.diego.pokedex.service;

import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.exception.ConflictException;
import me.diego.pokedex.model.Region;
import me.diego.pokedex.model.Trainer;
import me.diego.pokedex.model.UserModel;
import me.diego.pokedex.model.dto.TrainerPostDTO;
import me.diego.pokedex.repository.TrainerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final RegionService regionService;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthService authService;

    public TrainerService(TrainerRepository trainerRepository, RegionService regionService, UserDetailsServiceImpl userDetailsService, AuthService authService) {
        this.trainerRepository = trainerRepository;
        this.regionService = regionService;
        this.userDetailsService = userDetailsService;
        this.authService = authService;
    }

    public Page<Trainer> getAllTrainers(Pageable pageable) {
        return trainerRepository.findAll(pageable);
    }

    public Trainer findById(long id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> {
                    throw new BadRequestException("User not found");
                });
    }

    public List<Trainer> findByName(String name) {
        return trainerRepository.findByName(name);
    }

    @Transactional
    public Trainer saveTrainer(TrainerPostDTO trainer, Map<String, String> headers) {
        if (!(findByName(trainer.getName()).isEmpty())) {
            throw new ConflictException("Trainer with this name already exists");
        }

        Region region = regionService.findByRegionNameString(trainer.getRegionName());

        Trainer trainerToBeSaved = Trainer.builder()
                .name(trainer.getName())
                .age(trainer.getAge())
                .region(region)
                .build();

        Trainer trainerSaved = trainerRepository.save(trainerToBeSaved);

        String username = authService.getUsernameByJwt(headers);
        UserModel userModel = userDetailsService.loadUserModelByUsername(username);
        userModel.setTrainer(trainerSaved);
        userDetailsService.save(userModel);

        return trainerSaved;
    }
    @Transactional
    public Trainer updateTrainerPokemon(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    public Page<Trainer> getTrainerByRegion(String regionName, Pageable pageable) {
        Region regionFound = regionService.findByRegionNameString(regionName);

        return trainerRepository.findByRegion(regionFound, pageable);
    }
}

