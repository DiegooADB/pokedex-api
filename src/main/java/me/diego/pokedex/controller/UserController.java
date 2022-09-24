package me.diego.pokedex.controller;

import me.diego.pokedex.model.UserModel;
import me.diego.pokedex.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserModel register(@RequestBody UserModel userModel) {
        return userDetailsService.createNewUser(userModel);
    }


}
