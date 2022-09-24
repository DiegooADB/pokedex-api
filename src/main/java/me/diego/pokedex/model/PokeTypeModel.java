package me.diego.pokedex.model;

import lombok.Getter;
import lombok.Setter;
import me.diego.pokedex.enums.PokeType;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tb_poke_type")
public class PokeTypeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poke_type_id")
    private long id;

    @Column(name = "poke_type")
    @Enumerated(EnumType.STRING)
    private PokeType pokeType;
}
