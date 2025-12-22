package com.example.article_crud.domain.article;

import com.example.article_crud.common.security.dto.CurrentUserPrincipal;
import com.example.article_crud.domain.article.dto.ArticleResponse;
import com.example.article_crud.domain.article.dto.CreateArticleRequest;
import com.example.article_crud.domain.article.dto.UpdateArticleRequest;
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
    public ResponseEntity<List<ArticleResponse>> getArticles(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal
    ) {
        List<ArticleResponse> articleResponses = this.articleService.findAll();
        return ResponseEntity
                .ok(articleResponses);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponse> getArticle(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal,
            @PathVariable Long articleId
    ) {
        ArticleResponse articleResponse = this.articleService.findArticle(articleId);
        return ResponseEntity
                .ok(articleResponse);
    }

    @PostMapping
    public ResponseEntity<ArticleResponse> postArticle(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal,
            @RequestBody CreateArticleRequest request
    ) {
        CurrentUserDto currentUserDto = CurrentUserDto.from(currentUserPrincipal);

        ArticleResponse articleResponse = this.articleService
                .createArticle(request, currentUserDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(articleResponse);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<ArticleResponse> putArticle(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal,
            @PathVariable Long articleId,
            @RequestBody UpdateArticleRequest request
    ) {
        CurrentUserDto currentUserDto = CurrentUserDto.from(currentUserPrincipal);

        ArticleResponse articleResponse = this.articleService
                .updateArticle(articleId, request, currentUserDto);
        return ResponseEntity
                .ok(articleResponse);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> deleteArticle(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal,
            @PathVariable Long articleId
    ) {
        CurrentUserDto currentUserDto = CurrentUserDto.from(currentUserPrincipal);
        this.articleService.removeArticle(articleId, currentUserDto);
        return ResponseEntity
                .noContent()
                .build();
    }
}
