package me.diego.pokedex.service;

import me.diego.pokedex.config.SecurityConfig;
import me.diego.pokedex.enums.RoleName;
import me.diego.pokedex.model.Role;
import me.diego.pokedex.model.User;
import me.diego.pokedex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public User createNewUser(User user) {

        String passwordEncoded = securityConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(passwordEncoded);

        Role userRole = roleService.findRoleByName(RoleName.ROLE_USER);
        user.setRoles(List.of(userRole));

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
    }
}
