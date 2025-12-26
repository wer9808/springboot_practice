package com.example.post_crud.domain.auth.dto;

public record SignUpRequest(
        String email,
        String password,
        String username
) {

}
