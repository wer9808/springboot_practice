package com.example.article_crud.domain.article.dto;

import com.example.article_crud.domain.article.Article;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ArticleResponse(
        Long id,
        UUID authorId,
        String title,
        String content,
        Instant createdAt,
        Instant updatedAt
) {

    public static ArticleResponse from(Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getAuthorId(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedAt(),
                article.getUpdatedAt()
        );
    }

}


