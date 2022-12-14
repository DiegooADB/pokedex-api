package me.diego.pokedex.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import me.diego.pokedex.config.security.JwtTokenService;
import me.diego.pokedex.config.security.SecurityConfig;
import me.diego.pokedex.config.security.SecurityConstants;
import me.diego.pokedex.enums.RoleName;
import me.diego.pokedex.exception.ConflictException;
import me.diego.pokedex.model.Role;
import me.diego.pokedex.model.UserModel;
import me.diego.pokedex.model.dto.UserSignInDTO;
import me.diego.pokedex.model.dto.UserSignUpDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final SecurityConfig securityConfig;
    private final RoleService roleService;

    public AuthService(UserDetailsServiceImpl userDetailsService, JwtTokenService jwtTokenService, AuthenticationManager authenticationManager, SecurityConfig securityConfig, RoleService roleService) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
        this.securityConfig = securityConfig;
        this.roleService = roleService;
    }

    public String signInUser(UserSignInDTO userSignIn) {
        try {
            String username = userSignIn.getUsernameOrEmail();
            String userByEmail = userDetailsService.findUserByEmail(userSignIn.getUsernameOrEmail());

            if(!(userByEmail == null)) {
                username = userByEmail;
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username, userSignIn.getPassword()));
        } catch (final BadCredentialsException ex) {
            throw new BadCredentialsException("Username or password is incorrect");
        }

        final UserDetails user = userDetailsService.loadUserByUsername(userSignIn.getUsernameOrEmail());
        return jwtTokenService.generateToken(user);
    }

    @Transactional
    public String registerUser(UserSignUpDTO userDto) {
        if (userDetailsService.existsUsernameOrEmail(userDto)) {
            throw new ConflictException("Username or email is already taken!");
        }

        UserModel user = UserModel.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(securityConfig.getPasswordEncoder().encode(userDto.getPassword()))
                .build();

        Role userRole = roleService.findRoleByName(RoleName.ROLE_USER);
        user.setRoles(List.of(userRole));

        userDetailsService.save(user);
        return "User registered successfully";
    }

    public String getUsernameByJwt(Map<String, String> headers) {
        String token = headers.get(HttpHeaders.AUTHORIZATION.toLowerCase());

        String rawJwt = token.replace(SecurityConstants.PREFIX_TOKEN, "");
        DecodedJWT decode = JWT.decode(rawJwt);
        return decode.getSubject();
    }
}
