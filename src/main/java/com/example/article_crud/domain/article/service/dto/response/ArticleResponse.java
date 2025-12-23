package com.example.article_crud.domain.article.service.dto.response;

import com.example.article_crud.domain.article.ArticleStatus;
import com.example.article_crud.domain.article.repository.dto.ArticleListRow;

import java.time.Instant;
import java.util.UUID;

public record ArticleResponse(
        Long id,
        UUID authorId,
        String authorName,
        String title,
        String content,
        Instant createdAt,
        Instant updatedAt,
        ArticleStatus status
) {

    public static ArticleResponse from(ArticleListRow row) {
        return new ArticleResponse(
                row.id(),
                row.authorId(),
                row.authorName(),
                row.title(),
                row.content(),
                row.createdAt(),
                row.updatedAt(),
                row.status()
        );
    }

}


