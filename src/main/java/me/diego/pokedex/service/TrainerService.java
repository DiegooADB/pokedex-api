package me.diego.pokedex.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import me.diego.pokedex.config.security.SecurityConstants;
import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.exception.ConflictException;
import me.diego.pokedex.model.Region;
import me.diego.pokedex.model.Trainer;
import me.diego.pokedex.model.UserModel;
import me.diego.pokedex.model.dto.TrainerPostDTO;
import me.diego.pokedex.repository.TrainerRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final RegionService regionService;
    private final UserDetailsServiceImpl userDetailsService;

    public TrainerService(TrainerRepository trainerRepository, RegionService regionService, UserDetailsServiceImpl userDetailsService) {
        this.trainerRepository = trainerRepository;
        this.regionService = regionService;
        this.userDetailsService = userDetailsService;
    }

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

    @Transactional
    public Trainer saveTrainer(TrainerPostDTO trainer, Map<String, String> headers) {
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

        String username = this.getUsernameByJwt(headers);
        UserModel userModel = userDetailsService.loadUserModelByUsername(username);
        userModel.setTrainer(trainerSaved);
        userDetailsService.save(userModel);

        return trainerSaved;
    }
    @Transactional
    public Trainer updateTrainerPokemon(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    private String getUsernameByJwt(Map<String, String> headers) {
        String token = headers.get(HttpHeaders.AUTHORIZATION.toLowerCase());

        String rawJwt = token.replace(SecurityConstants.PREFIX_TOKEN, "");
        DecodedJWT decode = JWT.decode(rawJwt);
        return decode.getSubject();
    }
}

