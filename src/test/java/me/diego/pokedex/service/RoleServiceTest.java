package me.diego.pokedex.service;

import me.diego.pokedex.enums.RoleName;
import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.model.Role;
import me.diego.pokedex.repository.RoleRepository;
import me.diego.pokedex.util.RoleCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        BDDMockito.when(roleRepository.findByRoleName(ArgumentMatchers.any(RoleName.class)))
                .thenReturn(Optional.of(RoleCreator.createValidUserRole()));
    }

    @Test
    @DisplayName("findRoleByName returns role when successful")
    void findRoleByName_ReturnsRole_WhenSuccessful() {
        Role roleFound = roleService.findRoleByName(RoleName.ROLE_USER);

        Role expectedRole = RoleCreator.createValidUserRole();

        Assertions.assertThat(roleFound)
                .isNotNull();

        Assertions.assertThat(roleFound.getRoleName())
                .isEqualTo(expectedRole.getRoleName());

    }
}