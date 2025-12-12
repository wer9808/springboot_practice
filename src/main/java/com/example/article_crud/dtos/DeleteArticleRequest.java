package com.example.article_crud.dtos;

import java.util.UUID;

public record DeleteArticleRequest(
        UUID userId
) {
}
