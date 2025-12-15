package com.example.article_crud.domain.article;

import com.example.article_crud.domain.article.dto.ArticleResponse;
import com.example.article_crud.domain.article.dto.CreateArticleRequest;
import com.example.article_crud.domain.article.dto.DeleteArticleRequest;
import com.example.article_crud.domain.article.dto.UpdateArticleRequest;
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
    public List<ArticleResponse> getArticles() {
        return this.articleService.findAll();
    }

    @GetMapping("/{articleId}")
    public ArticleResponse getArticle(@PathVariable Long articleId) {
        return this.articleService.findArticle(articleId);
    }

    @PostMapping
    public ArticleResponse postArticle(@RequestBody CreateArticleRequest request) {
        return this.articleService
                .createArticle(request);
    }

    @PutMapping("/{articleId}")
    public ArticleResponse putArticle(@PathVariable Long articleId, @RequestBody UpdateArticleRequest request) {
        return this.articleService
                .updateArticle(articleId, request);
    }

    @DeleteMapping("/{articleId}")
    public void deleteArticle(@PathVariable Long articleId, @RequestBody DeleteArticleRequest request) {
        this.articleService.removeArticle(articleId, request);
    }
}
