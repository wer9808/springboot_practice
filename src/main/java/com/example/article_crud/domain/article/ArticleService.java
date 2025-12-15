package com.example.article_crud.domain.article;

import com.example.article_crud.domain.common.CommonErrorCode;
import com.example.article_crud.domain.article.dto.ArticleResponse;
import com.example.article_crud.domain.article.dto.CreateArticleRequest;
import com.example.article_crud.domain.article.dto.DeleteArticleRequest;
import com.example.article_crud.domain.article.dto.UpdateArticleRequest;
import com.example.article_crud.domain.common.exception.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    private void checkArticleUpdatePermission(UUID userId, Article article) throws BusinessException {
        if (!article.getAuthorId().equals(userId)) {
            throw new BusinessException(CommonErrorCode.PERMISSION_ACCESS_DENIED);
        }
    }

    public List<ArticleResponse> findAll() {
        return this.articleRepository
                .findAll()
                .stream()
                .map(ArticleResponse::from)
                .toList();
    }

    public ArticleResponse findArticle(Long articleId) throws BusinessException {
        Article article = this.articleRepository
                .findById(articleId)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.ARTICLE_NOT_FOUND));
        return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse createArticle(
            CreateArticleRequest request
    ) throws BusinessException {
        Article article = Article.of(request.userId(), request.title(), request.content());
        this.articleRepository.save(article);
        return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse updateArticle(
            Long articleId,
            UpdateArticleRequest request
    ) throws BusinessException {
        Article article = this.articleRepository
                .findById(articleId)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.ARTICLE_NOT_FOUND));
        checkArticleUpdatePermission(request.userId(), article);
        article.changeTitle(request.title());
        article.changeContent(request.content());

        return ArticleResponse.from(article);
    }

    @Transactional
    public void removeArticle(
            Long articleId,
            DeleteArticleRequest request
    ) throws BusinessException {
        Article article = this.articleRepository
                .findById(articleId)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.ARTICLE_NOT_FOUND));
        checkArticleUpdatePermission(request.userId(), article);
        this.articleRepository.delete(article);
    }

}
