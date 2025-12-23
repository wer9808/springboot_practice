package com.example.article_crud.domain.auth.dto;

import java.util.UUID;

public record SignUpResponse(
        UUID userId
) {
}
