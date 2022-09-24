package me.diego.pokedex.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_TRAINER")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Region region;

    @Column(nullable = false)
    private int age;

    @ManyToMany
    @JoinTable(name = "TB_TRAINER_POKEMONS",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "pokemon_id"))
    List<Pokemon> pokemons = new ArrayList<>();
}
