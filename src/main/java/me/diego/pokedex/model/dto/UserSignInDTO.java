package me.diego.pokedex.model.dto;

import lombok.Data;

@Data
public class UserSignInDTO {
    private String usernameOrEmail;
    private String password;
}