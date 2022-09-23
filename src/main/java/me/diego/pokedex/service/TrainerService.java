package me.diego.pokedex.service;

import me.diego.pokedex.dto.TrainerPostDTO;
import me.diego.pokedex.model.Region;
import me.diego.pokedex.model.Trainer;
import me.diego.pokedex.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {
    @Autowired
    TrainerRepository trainerRepository;

    @Autowired
    RegionService regionService;

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    public Trainer saveTrainer(TrainerPostDTO trainer) {
        Region region = regionService.findByRegionNameString(trainer.getRegionName());

        Trainer trainerToBeSaved = Trainer.builder()
                .name(trainer.getName())
                .age(trainer.getAge())
                .region(region)
                .build();


        return trainerRepository.save(trainerToBeSaved);
    }
}
