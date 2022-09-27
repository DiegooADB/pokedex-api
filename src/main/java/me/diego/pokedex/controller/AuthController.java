package me.diego.pokedex.controller;

import me.diego.pokedex.model.dto.UserSignInDTO;
import me.diego.pokedex.model.dto.UserSignUpDTO;
import me.diego.pokedex.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<String> signIn(@Valid @RequestBody UserSignInDTO userSignIn) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.signInUser(userSignIn));
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody UserSignUpDTO userSignUp) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.registerUser(userSignUp));
    }
}
