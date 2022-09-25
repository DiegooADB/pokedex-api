package me.diego.pokedex.controller;

import me.diego.pokedex.model.dto.UserSignInDTO;
import me.diego.pokedex.model.dto.UserSignUpDTO;
import me.diego.pokedex.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpDTO userSignUp) {
        return ResponseEntity.status(HttpStatus.OK).body(userDetailsService.registerUser(userSignUp));
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<String> signIn(@RequestBody UserSignInDTO userSignInDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userDetailsService.authenticateUser(userSignInDTO));
    }
}
