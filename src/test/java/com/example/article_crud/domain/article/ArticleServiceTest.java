package com.example.article_crud.domain.article;

import com.example.article_crud.domain.article.service.dto.response.ArticleListResponse;
import com.example.article_crud.domain.article.service.dto.response.ArticleResponse;
import com.example.article_crud.domain.article.service.ArticleService;
import com.example.article_crud.domain.article.service.dto.request.CreateArticleRequest;
import com.example.article_crud.domain.article.service.dto.request.UpdateArticleRequest;
import com.example.article_crud.common.exception.ApiErrorCode;
import com.example.article_crud.common.exception.service.BusinessException;
import com.example.article_crud.domain.article.repository.ArticleRepository;
import com.example.article_crud.domain.article.repository.dto.ArticleListRow;
import com.example.article_crud.domain.article.service.dto.response.CreateArticleResponse;
import com.example.article_crud.domain.article.service.dto.response.UpdateArticleResponse;
import com.example.article_crud.domain.user.dto.CurrentUserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
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
            ArticleListRow row1 = new ArticleListRow(1L, "title1", "content1", userId, Instant.now(), Instant.now());
            ArticleListRow row2 = new ArticleListRow(2L, "title2", "content2", userId, Instant.now(), Instant.now());
            given(articleRepository.findActiveArticleList()).willReturn(List.of(row1, row2));

            // when
            ArticleListResponse response = articleService.findAllArticles();

            // then
            assertEquals(2, response.articles().size());
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
            ArticleListRow row = new ArticleListRow(articleId, "title", "content", userId, Instant.now(), Instant.now());
            given(articleRepository.findActiveArticleListRowById(articleId)).willReturn(Optional.of(row));

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
            given(articleRepository.findActiveArticleListRowById(articleId)).willReturn(Optional.empty());

            // when & then
            BusinessException exception = assertThrows(BusinessException.class, () -> articleService.findArticle(articleId));
            assertEquals(ApiErrorCode.ARTICLE_NOT_FOUND, exception.getErrorCode());
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
            CurrentUserDto currentUserDto = new CurrentUserDto(userId, "email", "name");
            CreateArticleRequest request = new CreateArticleRequest("title", "content");
            Article article = Article.of(userId, request.title(), request.content());
            given(articleRepository.save(any(Article.class))).willReturn(article);

            // when
            CreateArticleResponse response = articleService.createArticle(request, currentUserDto);

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
            CurrentUserDto currentUserDto = new CurrentUserDto(userId, "email", "name");
            UpdateArticleRequest request = new UpdateArticleRequest("new title", "new content");
            Article article = Article.of(userId, "old title", "old content");
            given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

            // when
            UpdateArticleResponse response = articleService.updateArticle(articleId, request, currentUserDto);

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
            CurrentUserDto currentUserDto = new CurrentUserDto(userId, "email", "name");
            UpdateArticleRequest request = new UpdateArticleRequest("new title", "new content");
            given(articleRepository.findById(articleId)).willReturn(Optional.empty());

            // when & then
            BusinessException exception = assertThrows(BusinessException.class, () -> articleService.updateArticle(articleId, request, currentUserDto));
            assertEquals(ApiErrorCode.ARTICLE_NOT_FOUND, exception.getErrorCode());
        }

        @Test
        @DisplayName("실패 - 수정 권한이 없음")
        void fail_whenPermissionDenied() {
            // given
            Long articleId = 1L;
            UUID ownerId = UUID.randomUUID();
            UUID requesterId = UUID.randomUUID();
            CurrentUserDto currentUserDto = new CurrentUserDto(requesterId, "email", "name");
            UpdateArticleRequest request = new UpdateArticleRequest("new title", "new content");
            Article article = Article.of(ownerId, "old title", "old content");
            given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

            // when & then
            BusinessException exception = assertThrows(BusinessException.class, () -> articleService.updateArticle(articleId, request, currentUserDto));
            assertEquals(ApiErrorCode.PERMISSION_ACCESS_DENIED, exception.getErrorCode());
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
            CurrentUserDto currentUserDto = new CurrentUserDto(userId, "email", "name");
            Article article = Article.of(userId, "title", "content");
            given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

            // when
            assertDoesNotThrow(() -> articleService.removeArticle(articleId, currentUserDto));

            // then
            assertEquals(ArticleStatus.INACTIVE, article.getStatus());
        }

        @Test
        @DisplayName("실패 - 게시글을 찾을 수 없음")
        void fail_whenArticleNotFound() {
            // given
            Long articleId = 1L;
            UUID userId = UUID.randomUUID();
            CurrentUserDto currentUserDto = new CurrentUserDto(userId, "email", "name");
            given(articleRepository.findById(articleId)).willReturn(Optional.empty());

            // when & then
            BusinessException exception = assertThrows(BusinessException.class, () -> articleService.removeArticle(articleId, currentUserDto));
            assertEquals(ApiErrorCode.ARTICLE_NOT_FOUND, exception.getErrorCode());
        }

        @Test
        @DisplayName("실패 - 삭제 권한이 없음")
        void fail_whenPermissionDenied() {
            // given
            Long articleId = 1L;
            UUID ownerId = UUID.randomUUID();
            UUID requesterId = UUID.randomUUID();
            CurrentUserDto currentUserDto = new CurrentUserDto(requesterId, "email", "name");
            Article article = Article.of(ownerId, "title", "content");
            given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

            // when & then
            BusinessException exception = assertThrows(BusinessException.class, () -> articleService.removeArticle(articleId, currentUserDto));
            assertEquals(ApiErrorCode.PERMISSION_ACCESS_DENIED, exception.getErrorCode());
        }
    }
}
