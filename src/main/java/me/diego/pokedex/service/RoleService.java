package me.diego.pokedex.service;

import me.diego.pokedex.enums.RoleName;
import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.model.Role;
import me.diego.pokedex.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleByName(RoleName roleName) {

        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new BadRequestException("Role " + roleName.toString() + " not found"));

    }
}
