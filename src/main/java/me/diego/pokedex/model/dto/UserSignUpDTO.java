package me.diego.pokedex.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserSignUpDTO {
    @NotEmpty(message = "Username cannot be empty")
    private String username;
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
