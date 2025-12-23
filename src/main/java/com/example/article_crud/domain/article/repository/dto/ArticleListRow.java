package com.example.article_crud.domain.article.repository.dto;

import com.example.article_crud.domain.article.ArticleStatus;

import java.time.Instant;
import java.util.UUID;

public record ArticleListRow(
        Long id,
        UUID authorId,
        String authorName,
        String title,
        String content,
        Instant createdAt,
        Instant updatedAt,
        ArticleStatus status
) {
}
