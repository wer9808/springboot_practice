package com.example.article_crud.services;

import com.example.article_crud.CommonErrorCode;
import com.example.article_crud.dtos.ArticleDto;
import com.example.article_crud.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {

    private final List<ArticleDto> articles = new ArrayList<>(
            Arrays.asList(
                    new ArticleDto(1L, UUID.randomUUID(), "제목1", "내용1"),
                    new ArticleDto(2L, UUID.randomUUID(), "제목2", "내용2")
            )
    );

    private void checkArticleUpdatePermission(UUID userId, ArticleDto article) throws BusinessException {
        if (!article.getAuthorId().equals(userId)) {
            throw new BusinessException(CommonErrorCode.PERMISSION_ACCESS_DENIED);
        }
    }

    public List<ArticleDto> getArticles() {
        return this.articles;
    }

    public ArticleDto findArticle(Long articleId) throws BusinessException {
        ArticleDto article = this.articles
                .stream()
                .filter(a -> a.getId().equals(articleId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(CommonErrorCode.ARTICLE_NOT_FOUND));
        return article;
    }

    public ArticleDto createArticle(
            UUID authorId,
            String title,
            String content
    ) throws BusinessException {
        Long lastId = this.articles.get(this.articles.size() - 1).getId() + 1;
        ArticleDto newArticle = new ArticleDto(lastId, authorId, title, content);
        this.articles.add(newArticle);
        return newArticle;
    }

    public ArticleDto updateArticle(
            Long articleId,
            UUID userId,
            String title,
            String content
    ) throws BusinessException {
        ArticleDto article = findArticle(articleId);
        checkArticleUpdatePermission(userId, article);

        article.setTitle(title);
        article.setContent(content);
        article.setUpdatedAt(OffsetDateTime.now());

        return article;
    }

    public void removeArticle(
            Long articleId,
            UUID userId
    ) throws BusinessException {
        ArticleDto article = findArticle(articleId);
        checkArticleUpdatePermission(userId, article);
        this.articles.remove(article);
    }

}
