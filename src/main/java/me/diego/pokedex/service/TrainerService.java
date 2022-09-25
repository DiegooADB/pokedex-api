package me.diego.pokedex.service;

import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.exception.ConflictException;
import me.diego.pokedex.model.UserModel;
import me.diego.pokedex.model.dto.TrainerPostDTO;
import me.diego.pokedex.model.Region;
import me.diego.pokedex.model.Trainer;
import me.diego.pokedex.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {
    @Autowired
    TrainerRepository trainerRepository;

    @Autowired
    RegionService regionService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
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

    public Trainer saveTrainer(TrainerPostDTO trainer, UserDetails userDetails) {
        if (!(findByName(trainer.getName()).isEmpty())) {
            throw new ConflictException("Trainer with this name already exists", "trainer");
        }

        Region region = regionService.findByRegionNameString(trainer.getRegionName());

        Trainer trainerToBeSaved = Trainer.builder()
                .name(trainer.getName())
                .age(trainer.getAge())
                .region(region)
                .build();

        Trainer trainerSaved = trainerRepository.save(trainerToBeSaved);

        UserModel userModel = userDetailsService.loadUserModelByUsername(userDetails.getUsername());
        userModel.setTrainer(trainerSaved);
        userDetailsService.updateUser(userModel);

        return trainerSaved;
    }

    public Trainer updateTrainerPokemon(Trainer trainer) {
        return trainerRepository.save(trainer);
    }
}
