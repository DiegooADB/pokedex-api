package me.diego.pokedex.controller;

import me.diego.pokedex.model.Region;
import me.diego.pokedex.model.Trainer;
import me.diego.pokedex.model.dto.TrainerPostDTO;
import me.diego.pokedex.service.TrainerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/trainer")
public class TrainerController {
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping
    public ResponseEntity<Page<Trainer>> listAllTrainer(@PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.FOUND).body(trainerService.getAllTrainers(pageable));
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<Trainer> saveTrainer(@Valid @RequestBody TrainerPostDTO trainer, @RequestHeader Map<String, String> headers) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trainerService.saveTrainer(trainer, headers));
    }

    @GetMapping(path = "/region")
    public ResponseEntity<Page<Trainer>> findAllByRegion(@RequestParam String region, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.FOUND).body(trainerService.getTrainerByRegion(region, pageable));
    }
}
