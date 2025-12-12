package com.example.article_crud.support.fixtures;

import com.example.article_crud.dtos.ArticleDto;
import com.example.article_crud.dtos.CreateArticleRequest;
import com.example.article_crud.dtos.DeleteArticleRequest;
import com.example.article_crud.dtos.UpdateArticleRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArticleFixture {

    static final int DEFAULT_ARTICLE_COUNT = 10;
    static final String DEFAULT_ARTICLE_TITLE = "제목";
    static final String DEFAULT_ARTICLE_CONTENT = "내용";

    public static List<ArticleDto> default_articles() {
        List<ArticleDto> articles = new ArrayList<>();
        articles.add(
                default_article()
        );
        for (int i = 1; i < DEFAULT_ARTICLE_COUNT; i++) {
            articles.add(
                    new ArticleDto(
                            (long)i,
                            UserFixture.random_user_id(),
                            DEFAULT_ARTICLE_TITLE + i,
                            DEFAULT_ARTICLE_CONTENT + i
                    )
            );
        }
        return articles;
    }

    public static ArticleDto default_article() {
        return new ArticleDto(
                0L,
                UserFixture.default_user_id(),
                DEFAULT_ARTICLE_TITLE,
                DEFAULT_ARTICLE_CONTENT
        );
    }

}
