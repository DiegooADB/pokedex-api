package me.diego.pokedex.repository;

import me.diego.pokedex.model.PokeTypeModel;
import me.diego.pokedex.model.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    Page<Pokemon> findByCapturedFalse(Pageable pageable);
}
