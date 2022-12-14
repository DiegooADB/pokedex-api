package me.diego.pokedex.service;

import me.diego.pokedex.model.UserModel;
import me.diego.pokedex.model.dto.UserSignUpDTO;
import me.diego.pokedex.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

        return new User(
                userModel.getUsername(),
                userModel.getPassword(),
                true,
                true,
                true,
                true,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }


    public UserModel loadUserModelByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
    }

    public boolean existsUsernameOrEmail(UserSignUpDTO userSignUp) {
        return userRepository.existsByUsername(userSignUp.getUsername()) ||
                userRepository.existsByEmail(userSignUp.getEmail());
    }

    public String findUserByEmail(String email) {
        Optional<UserModel> userFound = userRepository.findByEmail(email);

        return userFound.map(UserModel::getUsername).orElse(null);
    }

    @Transactional
    public void save(UserModel userModel) {
        userRepository.save(userModel);
    }
}
