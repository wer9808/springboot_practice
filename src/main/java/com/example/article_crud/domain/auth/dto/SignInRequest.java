package com.example.article_crud.domain.auth.dto;

public record SignInRequest(
        String email,
        String password
) {
}
