package com.example.article_crud.domain.article.dto;

import java.util.UUID;

public record UpdateArticleRequest(
        UUID userId,
        String title,
        String content
) { }
