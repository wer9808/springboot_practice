package com.example.article_crud.domain.article.dto;

import java.util.UUID;

public record UpdateArticleRequest(
        String title,
        String content
) { }
