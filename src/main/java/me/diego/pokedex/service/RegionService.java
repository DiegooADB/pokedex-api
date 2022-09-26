package me.diego.pokedex.service;

import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.model.Region;
import me.diego.pokedex.repository.RegionRepository;
import org.springframework.stereotype.Service;

@Service
public class RegionService {
    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public Region findByRegionNameString(String regionName) {
        return regionRepository.findByRegionNameString(regionName.toUpperCase())
                .orElseThrow(() -> new BadRequestException("Region not found"));
    }
}
