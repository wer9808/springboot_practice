package com.example.article_crud.domain.article.service.dto.response;

import com.example.article_crud.domain.article.Article;

import java.time.Instant;

public record CreateArticleResponse(
        Long id,
        String title,
        Instant createdAt
) {
    public static CreateArticleResponse from(Article article) {
        return new CreateArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getCreatedAt()
        );
    }
}
