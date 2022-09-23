package me.diego.pokedex.repository;

import me.diego.pokedex.enums.RoleName;
import me.diego.pokedex.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);
}
