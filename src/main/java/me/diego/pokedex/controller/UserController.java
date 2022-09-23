package me.diego.pokedex.controller;

import me.diego.pokedex.model.User;
import me.diego.pokedex.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @PostMapping
    public User register(@RequestBody User user) {
        return userDetailsService.createNewUser(user);
    }


}
