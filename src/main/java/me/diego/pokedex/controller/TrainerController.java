package me.diego.pokedex.controller;

import me.diego.pokedex.model.Trainer;
import me.diego.pokedex.model.dto.TrainerPostDTO;
import me.diego.pokedex.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/trainer")
public class TrainerController {
    @Autowired
    TrainerService trainerService;

    @GetMapping
    public ResponseEntity<List<Trainer>> listAllTrainer() {
        return ResponseEntity.status(HttpStatus.FOUND).body(trainerService.getAllTrainers());
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<Trainer> saveTrainer(@RequestBody TrainerPostDTO trainer, @RequestHeader Map<String, String> headers) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trainerService.saveTrainer(trainer, headers));
    }
}
