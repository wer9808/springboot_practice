package com.example.article_crud.controllers;

import com.example.article_crud.dtos.ArticleDto;
import com.example.article_crud.dtos.CreateArticleRequest;
import com.example.article_crud.dtos.DeleteArticleRequest;
import com.example.article_crud.dtos.UpdateArticleRequest;
import com.example.article_crud.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.*;


@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<ArticleDto> getArticles() {
        return this.articleService.getArticles();
    }

    @GetMapping("/{articleId}")
    public ArticleDto getArticle(@PathVariable Long articleId) {
        return this.articleService.findArticle(articleId);
    }

    @PostMapping
    public ArticleDto createArticle(@RequestBody CreateArticleRequest request) {
        return this.articleService
                .createArticle(request.userId(), request.title(), request.content());
    }

    @PutMapping("/{articleId}")
    public ArticleDto updateArticle(@PathVariable Long articleId, @RequestBody UpdateArticleRequest request) {
        return this.articleService
                .updateArticle(articleId, request.userId(), request.title(), request.content());
    }

    @DeleteMapping("/{articleId}")
    public void deleteArticle(@PathVariable Long articleId, @RequestBody DeleteArticleRequest request) {
        this.articleService.removeArticle(articleId, request.userId());
    }
}
