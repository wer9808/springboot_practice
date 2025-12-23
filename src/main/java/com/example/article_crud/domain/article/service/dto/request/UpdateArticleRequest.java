package com.example.article_crud.domain.article.service.dto.request;

public record UpdateArticleRequest(
        String title,
        String content
) { }
