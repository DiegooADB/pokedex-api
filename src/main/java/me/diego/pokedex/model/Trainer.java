package me.diego.pokedex.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "TB_TRAINER")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinTable(name = "TB_TRAINER_REGION",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "region_id"))
    private Region region;

    @Column(nullable = false)
    private int age;

    @ManyToMany
    @JoinTable(name = "TB_TRAINER_POKEMONS",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "pokemon_id"))
    private List<Pokemon> pokemons = new ArrayList<>();

    public Trainer(long id, String name, Region region, int age, List<Pokemon> pokemons) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.age = age;
        this.pokemons = pokemons;
    }

    public Trainer() {
    }
}
