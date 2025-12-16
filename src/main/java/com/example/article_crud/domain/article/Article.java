package com.example.article_crud.domain.article;

import com.example.article_crud.domain.common.CommonErrorCode;
import com.example.article_crud.domain.common.exception.BusinessException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id", nullable = false)
    private UUID authorId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;

    private Article(UUID authorId, String title, String content) {
        this.authorId = authorId;
        this.title = title;
        this.content = content;
    }

    private static void validateTitle(String title) throws BusinessException {
        if (title == null || title.isBlank()) {
            throw new BusinessException(CommonErrorCode.ARTICLE_NO_TITLE);
        }
    }

    private static void validateContent(String content) throws BusinessException {
        if (content == null || content.isBlank()) {
            throw new BusinessException(CommonErrorCode.ARTICLE_NO_CONTENT);
        }
    }

    public static Article of(UUID authorId, String title, String content) throws BusinessException {
        validateTitle(title);
        validateContent(content);
        return new Article(authorId, title, content);
    }

    public void changeTitle(String title) throws BusinessException {
        validateTitle(title);
        this.title = title;
    }

    public void changeContent(String content) throws BusinessException {
        validateContent(content);
        this.content = content;
    }

}
