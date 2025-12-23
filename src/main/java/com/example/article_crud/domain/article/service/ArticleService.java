package com.example.article_crud.domain.article.service;

import com.example.article_crud.common.exception.ApiErrorCode;
import com.example.article_crud.domain.article.Article;
import com.example.article_crud.domain.article.ArticleStatus;
import com.example.article_crud.common.exception.service.BusinessException;
import com.example.article_crud.domain.article.repository.ArticleRepository;
import com.example.article_crud.domain.article.repository.dto.ArticleListRow;
import com.example.article_crud.domain.article.service.dto.response.ArticleListResponse;
import com.example.article_crud.domain.article.service.dto.response.ArticleResponse;
import com.example.article_crud.domain.article.service.dto.request.CreateArticleRequest;
import com.example.article_crud.domain.article.service.dto.request.UpdateArticleRequest;
import com.example.article_crud.domain.article.service.dto.response.CreateArticleResponse;
import com.example.article_crud.domain.article.service.dto.response.UpdateArticleResponse;
import com.example.article_crud.domain.user.dto.CurrentUserDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
            throw new BusinessException(ApiErrorCode.PERMISSION_ACCESS_DENIED);
        }
    }

    @Transactional
    public ArticleListResponse findAllArticles() {
        List<ArticleListRow> rows = this.articleRepository
                .findActiveArticleList();
        return ArticleListResponse.from(rows);
    }

    @Transactional
    public List<ArticleResponse> findMyArticles(CurrentUserDto currentUserDto) {
        return this.articleRepository
                .findArticleListByAuthorId(currentUserDto.id())
                .stream().map(ArticleResponse::from)
                .toList();
    }

    @Transactional
    public ArticleListResponse findUserArticles(UUID userId) {
        List<ArticleListRow> rows = this.articleRepository
                .findActiveArticleListByAuthorId(userId);
        return ArticleListResponse.from(rows);
    }

    @Transactional
    public ArticleResponse findArticle(Long articleId) throws BusinessException {
        ArticleListRow row = this.articleRepository
                .findActiveArticleListRowById(articleId)
                .orElseThrow(() -> new BusinessException(ApiErrorCode.ARTICLE_NOT_FOUND));
        return ArticleResponse.from(row);
    }

    @Transactional
    public CreateArticleResponse createArticle(
            CreateArticleRequest request,
            CurrentUserDto currentUserDto
    ) throws BusinessException {
        Article article = Article.of(currentUserDto.id(), request.title(), request.content());
        this.articleRepository.save(article);
        return CreateArticleResponse.from(article);
    }

    @Transactional
    public UpdateArticleResponse updateArticle(
            Long articleId,
            UpdateArticleRequest request,
            CurrentUserDto currentUserDto
    ) throws BusinessException {
        Article article = this.articleRepository
                .findById(articleId)
                .orElseThrow(() -> new BusinessException(ApiErrorCode.ARTICLE_NOT_FOUND));
        checkArticleUpdatePermission(currentUserDto.id(), article);
        article.changeTitle(request.title());
        article.changeContent(request.content());
        return UpdateArticleResponse.from(article);
    }

    @Transactional
    public void removeArticle(
            Long articleId,
            CurrentUserDto currentUserDto
    ) throws BusinessException {
        Article article = this.articleRepository
                .findById(articleId)
                .orElseThrow(() -> new BusinessException(ApiErrorCode.ARTICLE_NOT_FOUND));
        checkArticleUpdatePermission(currentUserDto.id(), article);
        article.changeStatus(ArticleStatus.INACTIVE);
    }

}
