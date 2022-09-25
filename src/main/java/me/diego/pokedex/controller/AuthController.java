package me.diego.pokedex.controller;

import me.diego.pokedex.model.dto.UserSignInDTO;
import me.diego.pokedex.model.dto.UserSignUpDTO;
import me.diego.pokedex.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping(path = "/signin")
    public ResponseEntity<String> signIn(@RequestBody UserSignInDTO userSignIn) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.signInUser(userSignIn));
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpDTO userSignUp) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.registerUser(userSignUp));
    }
}
