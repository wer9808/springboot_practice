package com.example.article_crud.domain.article.service.dto.response;

import com.example.article_crud.domain.article.repository.dto.ArticleListRow;

import java.time.Instant;
import java.util.List;

public record ArticleListResponse(
        List<ArticleResponse> articles
) {

    public static ArticleListResponse from(List<ArticleListRow> rows) {
        List<ArticleResponse> responses = rows.stream()
                .map(ArticleResponse::from)
                .toList();

        return new ArticleListResponse(responses);
    }

}
