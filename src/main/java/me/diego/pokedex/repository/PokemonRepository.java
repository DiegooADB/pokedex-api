package me.diego.pokedex.repository;

import me.diego.pokedex.model.PokeTypeModel;
import me.diego.pokedex.model.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    Page<Pokemon> findByCapturedFalse(Pageable pageable);

    @Query(value =
            " SELECT poke_id, captured, image_url, poke_name " +
            "FROM tb_pokemon_type pt, tb_pokemon p, tb_poke_type t " +
            "where pt.pokemon_id = p.poke_id and " +
            "captured = 0 and " +
            "t.poke_type_id = pt.type_id " +
            "and t.poke_type = ?1"
            , nativeQuery = true)
    Page<Pokemon> findPokemonNotCapturedByPokeType(String typeName, Pageable pageable);
}
