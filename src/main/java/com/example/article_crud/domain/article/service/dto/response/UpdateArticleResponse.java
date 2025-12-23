package com.example.article_crud.domain.article.service.dto.response;

import com.example.article_crud.domain.article.Article;

import java.time.Instant;

public record UpdateArticleResponse(
        Long id,
        String title,
        String content,
        Instant createdAt,
        Instant updatedAt
) {
    public static UpdateArticleResponse from(Article article) {
        return new UpdateArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedAt(),
                article.getUpdatedAt()
        );
    }
}
