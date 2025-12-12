package com.example.article_crud.dtos;

import java.util.UUID;

public record UpdateArticleRequest(
        UUID userId,
        String title,
        String content
) { }
