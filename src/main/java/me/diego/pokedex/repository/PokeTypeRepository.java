package me.diego.pokedex.repository;

import me.diego.pokedex.model.PokeTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokeTypeRepository extends JpaRepository<PokeTypeModel, Long> {
    @Query(value = " SELECT * FROM tb_poke_type WHERE poke_type = ?1 ", nativeQuery = true)
    Optional<PokeTypeModel> findByPokeTypeString(String poketype);
}
