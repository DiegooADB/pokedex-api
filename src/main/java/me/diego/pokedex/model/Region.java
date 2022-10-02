package me.diego.pokedex.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.diego.pokedex.enums.RegionName;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "TB_REGION")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RegionName regionName;

    public Region() {
    }

    public Region(long id, RegionName regionName) {
        this.id = id;
        this.regionName = regionName;
    }
}
