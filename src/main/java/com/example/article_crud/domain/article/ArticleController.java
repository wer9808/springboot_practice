package com.example.article_crud.domain.article;

import com.example.article_crud.common.security.dto.CurrentUserPrincipal;
import com.example.article_crud.domain.article.service.dto.response.ArticleListResponse;
import com.example.article_crud.domain.article.service.dto.response.ArticleResponse;
import com.example.article_crud.domain.article.service.ArticleService;
import com.example.article_crud.domain.article.service.dto.request.CreateArticleRequest;
import com.example.article_crud.domain.article.service.dto.request.UpdateArticleRequest;
import com.example.article_crud.domain.article.service.dto.response.CreateArticleResponse;
import com.example.article_crud.domain.article.service.dto.response.UpdateArticleResponse;
import com.example.article_crud.domain.user.dto.CurrentUserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<ArticleListResponse> getArticles(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal,
            @RequestParam(required = false) UUID userId
    ) {
        ArticleListResponse response;
        if (userId != null) {
            response = this.articleService.findUserArticles(userId);
        }
        else {
            response = this.articleService.findAllArticles();
        }
        return ResponseEntity
                .ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<List<ArticleResponse>> getMyArticles(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal
    ) {
        CurrentUserDto currentUserDto = CurrentUserDto.from(currentUserPrincipal);
        List<ArticleResponse> responses = this.articleService.findMyArticles(currentUserDto);
        return ResponseEntity
                .ok(responses);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponse> getArticle(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal,
            @PathVariable Long articleId
    ) {
        ArticleResponse response = this.articleService.findArticle(articleId);
        return ResponseEntity
                .ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateArticleResponse> postArticle(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal,
            @RequestBody CreateArticleRequest request
    ) {
        CurrentUserDto currentUserDto = CurrentUserDto.from(currentUserPrincipal);

        CreateArticleResponse response = this.articleService
                .createArticle(request, currentUserDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<UpdateArticleResponse> putArticle(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal,
            @PathVariable Long articleId,
            @RequestBody UpdateArticleRequest request
    ) {
        CurrentUserDto currentUserDto = CurrentUserDto.from(currentUserPrincipal);

        UpdateArticleResponse response = this.articleService
                .updateArticle(articleId, request, currentUserDto);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> deleteArticle(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal,
            @PathVariable Long articleId
    ) {
        CurrentUserDto currentUserDto = CurrentUserDto.from(currentUserPrincipal);
        this.articleService.removeArticle(articleId, currentUserDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
