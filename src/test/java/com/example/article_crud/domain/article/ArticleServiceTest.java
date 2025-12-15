package com.example.article_crud.domain.article;

import com.example.article_crud.domain.article.dto.ArticleResponse;
import com.example.article_crud.domain.article.dto.CreateArticleRequest;
import com.example.article_crud.domain.article.dto.DeleteArticleRequest;
import com.example.article_crud.domain.article.dto.UpdateArticleRequest;
import com.example.article_crud.domain.common.CommonErrorCode;
import com.example.article_crud.domain.common.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Nested
    @DisplayName("게시글 전체 조회")
    class FindAllArticles {
        @Test
        @DisplayName("성공")
        void success() {
            // given
            UUID userId = UUID.randomUUID();
            Article article1 = Article.of(userId, "title1", "content1");
            Article article2 = Article.of(userId, "title2", "content2");
            given(articleRepository.findAll()).willReturn(List.of(article1, article2));

            // when
            List<ArticleResponse> responses = articleService.findAll();

            // then
            assertEquals(2, responses.size());
        }
    }

    @Nested
    @DisplayName("게시글 단건 조회")
    class FindOneArticle {
        @Test
        @DisplayName("성공")
        void success() {
            // given
            Long articleId = 1L;
            UUID userId = UUID.randomUUID();
            Article article = Article.of(userId, "title", "content");
            given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

            // when
            ArticleResponse response = articleService.findArticle(articleId);

            // then
            assertNotNull(response);
            assertEquals("title", response.title());
        }

        @Test
        @DisplayName("실패 - 게시글을 찾을 수 없음")
        void fail_whenArticleNotFound() {
            // given
            Long articleId = 1L;
            given(articleRepository.findById(articleId)).willReturn(Optional.empty());

            // when & then
            BusinessException exception = assertThrows(BusinessException.class, () -> articleService.findArticle(articleId));
            assertEquals(CommonErrorCode.ARTICLE_NOT_FOUND, exception.getErrorCode());
        }
    }


    @Nested
    @DisplayName("게시글 생성")
    class CreateArticle {
        @Test
        @DisplayName("성공")
        void success() {
            // given
            UUID userId = UUID.randomUUID();
            CreateArticleRequest request = new CreateArticleRequest(userId, "title", "content");
            Article article = Article.of(request.userId(), request.title(), request.content());
            given(articleRepository.save(any(Article.class))).willReturn(article);

            // when
            ArticleResponse response = articleService.createArticle(request);

            // then
            assertNotNull(response);
            assertEquals("title", response.title());
            verify(articleRepository, times(1)).save(any(Article.class));
        }
    }

    @Nested
    @DisplayName("게시글 수정")
    class UpdateArticle {
        @Test
        @DisplayName("성공")
        void success() {
            // given
            Long articleId = 1L;
            UUID userId = UUID.randomUUID();
            UpdateArticleRequest request = new UpdateArticleRequest(userId, "new title", "new content");
            Article article = Article.of(userId, "old title", "old content");
            given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

            // when
            ArticleResponse response = articleService.updateArticle(articleId, request);

            // then
            assertNotNull(response);
            assertEquals("new title", response.title());
            assertEquals("new content", response.content());
        }

        @Test
        @DisplayName("실패 - 게시글을 찾을 수 없음")
        void fail_whenArticleNotFound() {
            // given
            Long articleId = 1L;
            UUID userId = UUID.randomUUID();
            UpdateArticleRequest request = new UpdateArticleRequest(userId, "new title", "new content");
            given(articleRepository.findById(articleId)).willReturn(Optional.empty());

            // when & then
            BusinessException exception = assertThrows(BusinessException.class, () -> articleService.updateArticle(articleId, request));
            assertEquals(CommonErrorCode.ARTICLE_NOT_FOUND, exception.getErrorCode());
        }

        @Test
        @DisplayName("실패 - 수정 권한이 없음")
        void fail_whenPermissionDenied() {
            // given
            Long articleId = 1L;
            UUID ownerId = UUID.randomUUID();
            UUID requesterId = UUID.randomUUID();
            UpdateArticleRequest request = new UpdateArticleRequest(requesterId, "new title", "new content");
            Article article = Article.of(ownerId, "old title", "old content");
            given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

            // when & then
            BusinessException exception = assertThrows(BusinessException.class, () -> articleService.updateArticle(articleId, request));
            assertEquals(CommonErrorCode.PERMISSION_ACCESS_DENIED, exception.getErrorCode());
        }
    }

    @Nested
    @DisplayName("게시글 삭제")
    class RemoveArticle {
        @Test
        @DisplayName("성공")
        void success() {
            // given
            Long articleId = 1L;
            UUID userId = UUID.randomUUID();
            DeleteArticleRequest request = new DeleteArticleRequest(userId);
            Article article = Article.of(userId, "title", "content");
            given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

            // when
            assertDoesNotThrow(() -> articleService.removeArticle(articleId, request));

            // then
            verify(articleRepository, times(1)).delete(article);
        }

        @Test
        @DisplayName("실패 - 게시글을 찾을 수 없음")
        void fail_whenArticleNotFound() {
            // given
            Long articleId = 1L;
            UUID userId = UUID.randomUUID();
            DeleteArticleRequest request = new DeleteArticleRequest(userId);
            given(articleRepository.findById(articleId)).willReturn(Optional.empty());

            // when & then
            BusinessException exception = assertThrows(BusinessException.class, () -> articleService.removeArticle(articleId, request));
            assertEquals(CommonErrorCode.ARTICLE_NOT_FOUND, exception.getErrorCode());
        }

        @Test
        @DisplayName("실패 - 삭제 권한이 없음")
        void fail_whenPermissionDenied() {
            // given
            Long articleId = 1L;
            UUID ownerId = UUID.randomUUID();
            UUID requesterId = UUID.randomUUID();
            DeleteArticleRequest request = new DeleteArticleRequest(requesterId);
            Article article = Article.of(ownerId, "title", "content");
            given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

            // when & then
            BusinessException exception = assertThrows(BusinessException.class, () -> articleService.removeArticle(articleId, request));
            assertEquals(CommonErrorCode.PERMISSION_ACCESS_DENIED, exception.getErrorCode());
        }
    }
}
