package me.diego.pokedex.service;

import me.diego.pokedex.enums.RoleName;
import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.model.Role;
import me.diego.pokedex.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role findRoleByName(RoleName roleName) {

        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new BadRequestException("Role " + roleName.toString() + " not found"));

    }
}
