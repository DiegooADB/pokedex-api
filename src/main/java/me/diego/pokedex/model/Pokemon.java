package me.diego.pokedex.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_POKEMON")
public class Pokemon {

    @Id
    @GeneratedValue
    @Column(name = "poke_id")
    private long pokeId;

    @Column(nullable = false, name = "poke_name")
    private String pokeName;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    @ManyToMany
    @JoinTable(name = "TB_POKEMON_TYPE",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    private List<PokeTypeModel> attributes;

    @Column(nullable = false, name = "captured")
    private boolean captured;
}
