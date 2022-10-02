package me.diego.pokedex.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.diego.pokedex.enums.PokeType;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "tb_poke_type")
public class PokeTypeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poke_type_id")
    private long id;

    @Column(name = "poke_type", unique = true)
    @Enumerated(EnumType.STRING)
    private PokeType pokeType;

    public PokeTypeModel() {
    }

    public PokeTypeModel(long id, PokeType pokeType) {
        this.id = id;
        this.pokeType = pokeType;
    }
}
