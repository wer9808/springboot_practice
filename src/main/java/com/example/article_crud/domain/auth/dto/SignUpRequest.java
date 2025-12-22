package com.example.article_crud.domain.auth.dto;

public record SignUpRequest(
        String email,
        String password,
        String username
) {

}
