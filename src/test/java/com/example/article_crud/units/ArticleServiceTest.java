package com.example.article_crud.units;

import com.example.article_crud.dtos.ArticleDto;
import com.example.article_crud.exceptions.BusinessException;
import com.example.article_crud.services.ArticleService;
import com.example.article_crud.support.fixtures.UserFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @InjectMocks
    ArticleService articleService;

    @Test
    void createArticleSuccess() throws BusinessException {
        // given
        UUID random_user_id = UserFixture.random_user_id();
        String title = "new title";
        String content = "new content";

        // when
        ArticleDto newArticle = articleService.createArticle(
                random_user_id,
                title,
                content
        );

        // then
        assertThat(newArticle.getAuthorId()).isEqualTo(random_user_id);
        assertThat(newArticle.getTitle()).isEqualTo(title);
        assertThat(newArticle.getContent()).isEqualTo(content);

        ArticleDto foundArticle = articleService.findArticle(newArticle.getId());
        assertThat(foundArticle).isEqualTo(newArticle);
    }

}
