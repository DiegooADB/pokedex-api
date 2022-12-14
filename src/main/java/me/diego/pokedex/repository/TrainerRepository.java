package me.diego.pokedex.repository;

import me.diego.pokedex.model.Region;
import me.diego.pokedex.model.Trainer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    List<Trainer> findByName(String name);

    Page<Trainer> findByRegion(Region region, Pageable pageable);
}
