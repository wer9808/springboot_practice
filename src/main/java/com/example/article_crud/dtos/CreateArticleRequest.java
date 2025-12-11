package com.example.article_crud.dtos;

import java.util.UUID;

public record CreateArticleRequest(
    UUID userId,
    String title,
    String content
) {}

