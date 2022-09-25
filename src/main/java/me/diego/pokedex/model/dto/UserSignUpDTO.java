package me.diego.pokedex.model.dto;

import lombok.Data;

@Data
public class UserSignUpDTO {
    private String username;
    private String email;
    private String password;
}
