package me.diego.pokedex.service;

import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.model.Region;
import me.diego.pokedex.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionService {
    @Autowired
    RegionRepository regionRepository;

    public Region findByRegionNameString(String regionName) {
        return regionRepository.findByRegionNameString(regionName.toUpperCase())
                .orElseThrow(() -> new BadRequestException("Region not found"));
    }
}
