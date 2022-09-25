package me.diego.pokedex.service;

import me.diego.pokedex.config.security.SecurityConfig;
import me.diego.pokedex.enums.RoleName;
import me.diego.pokedex.exception.ConflictException;
import me.diego.pokedex.model.Role;
import me.diego.pokedex.model.UserModel;
import me.diego.pokedex.model.dto.UserResponseDTO;
import me.diego.pokedex.model.dto.UserSignInDTO;
import me.diego.pokedex.model.dto.UserSignUpDTO;
import me.diego.pokedex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    AuthenticationManager authenticationManager;

    public String registerUser(UserSignUpDTO userDto) {
        if(userRepository.existsByUsername(userDto.getUsername())) {
            throw new ConflictException("User already exists", "user");
        }

        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new ConflictException("Email is already taken!", "email");
        }

        UserModel user = UserModel.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(securityConfig.passwordEncoder().encode(userDto.getPassword()))
                .build();

        Role userRole = roleService.findRoleByName(RoleName.ROLE_USER);
        user.setRoles(List.of(userRole));

        userRepository.save(user);
        return "User registered successfully";
    }

    public String authenticateUser(UserSignInDTO signInDTO) {

        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        signInDTO.getUsernameOrEmail(), signInDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return "User signed-in successfully!";
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

        return new User(
                userModel.getEmail(),
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

    public void updateUser(UserModel userModel) {
        userRepository.save(userModel);
    }
}
