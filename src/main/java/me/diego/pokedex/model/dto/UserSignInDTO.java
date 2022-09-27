package me.diego.pokedex.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserSignInDTO {
    @NotEmpty(message = "Email or username cannot be empty")
    private String usernameOrEmail;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
