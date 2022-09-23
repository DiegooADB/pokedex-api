package me.diego.pokedex.model;

import lombok.Getter;
import lombok.Setter;
import me.diego.pokedex.enums.RegionName;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "TB_REGION")
public class Region {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RegionName regionName;
}
