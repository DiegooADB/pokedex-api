package me.diego.pokedex.service;

import me.diego.pokedex.config.security.SecurityConfig;
import me.diego.pokedex.enums.RoleName;
import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.model.Role;
import me.diego.pokedex.model.UserModel;
import me.diego.pokedex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SecurityConfig securityConfig;

    @Autowired
    RoleService roleService;

    public UserModel createNewUser(UserModel userModel) {

        userRepository.findByUsername(userModel.getUsername()).ifPresent(userModel1 -> {
            throw new BadRequestException("User already exists");
        });

        String passwordEncoded = securityConfig.passwordEncoder().encode(userModel.getPassword());
        userModel.setPassword(passwordEncoded);

        Role userRole = roleService.findRoleByName(RoleName.ROLE_USER);
        userModel.setRoles(List.of(userRole));

        return userRepository.save(userModel);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));

        return new User(
                userModel.getUsername(),
                userModel.getPassword(),
                true,
                true,
                true,
                true,
                userModel.getAuthorities());
    }

    public UserModel loadUserModelByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
    }

    public UserModel saveUser(UserModel userModel) {
        return userRepository.save(userModel);
    }
}
