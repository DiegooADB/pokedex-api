package me.diego.pokedex.repository;

import me.diego.pokedex.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
    @Query(value = " SELECT * FROM tb_region WHERE region_name = ?1 ", nativeQuery = true)
    Optional<Region> findByRegionNameString(String regionName);
}
