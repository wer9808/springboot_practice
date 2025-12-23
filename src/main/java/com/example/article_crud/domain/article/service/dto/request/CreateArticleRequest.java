package com.example.article_crud.domain.article.service.dto.request;

public record CreateArticleRequest(
    String title,
    String content
) {}

