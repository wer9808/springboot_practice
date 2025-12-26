package com.example.post_crud.domain.auth.dto;

public record SignInRequest(
        String email,
        String password
) {
}
