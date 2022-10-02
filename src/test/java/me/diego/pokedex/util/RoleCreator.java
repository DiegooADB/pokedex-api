package me.diego.pokedex.util;

import me.diego.pokedex.enums.RoleName;
import me.diego.pokedex.model.Role;

public class RoleCreator {
    public static Role createValidUserRole() {
        return Role.builder()
                .roleId(1L)
                .roleName(RoleName.ROLE_USER)
                .build();
    }

    public static Role createValidAdminRole() {
        return Role.builder()
                .roleId(1L)
                .roleName(RoleName.ROLE_ADMIN)
                .build();
    }
}
