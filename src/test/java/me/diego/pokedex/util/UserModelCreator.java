package me.diego.pokedex.util;

import me.diego.pokedex.model.UserModel;

import java.util.List;

public class UserModelCreator {
    public static UserModel createValidUser() {
        return UserModel.builder()
                .id(1L)
                .username("UserTest")
                .trainer(TrainerCreator.createValidTrainer())
                .email("UserTest@test.com")
                .password("123")
                .roles(List.of(RoleCreator.createValidUserRole()))
                .build();
    }

    public static UserModel createValidAdmin() {
        return UserModel.builder()
                .id(1L)
                .username("AdminTest")
                .trainer(TrainerCreator.createValidTrainer())
                .email("AdminTest@test.com")
                .password("123")
                .roles(List.of(RoleCreator.createValidAdminRole()))
                .build();
    }
}
