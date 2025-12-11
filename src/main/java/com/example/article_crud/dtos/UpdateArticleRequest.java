package com.example.article_crud.dtos;

public record UpdateArticleRequest(
        String title,
        String content
) { }
