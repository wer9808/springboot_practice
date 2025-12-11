package com.example.article_crud.controllers;

import com.example.article_crud.dtos.ArticleDto;
import com.example.article_crud.dtos.CreateArticleRequest;
import com.example.article_crud.dtos.UpdateArticleRequest;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.*;


@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final List<ArticleDto> articles = new ArrayList<>(
            Arrays.asList(
                    new ArticleDto(1L, UUID.randomUUID(), "제목1", "내용1"),
                    new ArticleDto(2L, UUID.randomUUID(), "제목2", "내용2")
            )
    );

    @GetMapping
    public List<ArticleDto> getArticles() {
        return List.copyOf(this.articles);
    }

    @GetMapping("/{articleId}")
    public ArticleDto getArticle(@PathVariable Long articleId) {
        return this.articles.stream()
                .filter(article -> article.getId().equals(articleId))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public ArticleDto createArticle(@RequestBody CreateArticleRequest request) {
        Long lastId = this.articles.get(this.articles.size() - 1).getId();
        ArticleDto newArticle = new ArticleDto(lastId + 1, request.userId(), request.title(), request.content());
        this.articles.add(newArticle);
        return newArticle;
    }

    @PutMapping("/{articleId}")
    public ArticleDto updateArticle(@PathVariable Long articleId, @RequestBody UpdateArticleRequest request) {
        ArticleDto article = this.articles.stream()
                .filter(a -> a.getId().equals(articleId))
                .findFirst()
                .orElse(null);
        if (article != null) {
            article.setTitle(request.title());
            article.setContent(request.content());
            article.setUpdatedAt(OffsetDateTime.now());
        }
        return article;
    }

    @DeleteMapping("/{articleId}")
    public void deleteArticle(@PathVariable Long articleId) {
        this.articles.removeIf(article -> article.getId().equals(articleId));
    }

}
