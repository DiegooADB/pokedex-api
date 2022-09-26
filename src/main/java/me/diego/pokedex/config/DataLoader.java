package me.diego.pokedex.config;

import me.diego.pokedex.enums.PokeType;
import me.diego.pokedex.enums.RegionName;
import me.diego.pokedex.enums.RoleName;
import me.diego.pokedex.model.PokeTypeModel;
import me.diego.pokedex.model.Region;
import me.diego.pokedex.model.Role;
import me.diego.pokedex.repository.PokeTypeRepository;
import me.diego.pokedex.repository.RegionRepository;
import me.diego.pokedex.repository.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    private final RegionRepository regionRepository;
    private final RoleRepository roleRepository;
    private final PokeTypeRepository pokeTypeRepository;

    public DataLoader(RegionRepository regionRepository, RoleRepository roleRepository, PokeTypeRepository pokeTypeRepository) {
        this.regionRepository = regionRepository;
        this.roleRepository = roleRepository;
        this.pokeTypeRepository = pokeTypeRepository;
    }

    @Override
    public void run(ApplicationArguments args) {

        if (regionRepository.findAll().isEmpty()) {
            regionSave();
        }

        if (roleRepository.findAll().isEmpty()) {
            roleSave();
        }

        if (pokeTypeRepository.findAll().isEmpty()) {
            pokeTypeSave();
        }
    }

    private void regionSave() {
        for (RegionName regionName : RegionName.values()) {

            Region region = new Region();
            region.setRegionName(regionName);

            regionRepository.save(region);
        }
    }

    private void roleSave() {
        for (RoleName roleName : RoleName.values()) {

            Role role = new Role();
            role.setRoleName(roleName);

            roleRepository.save(role);
        }
    }

    private void pokeTypeSave() {
        for (PokeType pokeType : PokeType.values()) {
            PokeTypeModel pokeTypeModel = new PokeTypeModel();
            pokeTypeModel.setPokeType(pokeType);

            pokeTypeRepository.save(pokeTypeModel);
        }
    }
}
