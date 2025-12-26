package com.example.post_crud.domain.post;

import com.example.post_crud.common.exception.ApiErrorCode;
import com.example.post_crud.common.exception.service.BusinessException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

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

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus status;


    private Post(UUID authorId, String title, String content) {
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.status = PostStatus.ACTIVE;
    }

    private static void validateTitle(String title) throws BusinessException {
        if (title == null || title.isBlank()) {
            throw new BusinessException(ApiErrorCode.POST_NO_TITLE);
        }
    }

    private static void validateContent(String content) throws BusinessException {
        if (content == null || content.isBlank()) {
            throw new BusinessException(ApiErrorCode.POST_NO_CONTENT);
        }
    }

    public static Post of(UUID authorId, String title, String content) throws BusinessException {
        validateTitle(title);
        validateContent(content);
        return new Post(authorId, title, content);
    }

    public void changeTitle(String title) throws BusinessException {
        validateTitle(title);
        this.title = title;
    }

    public void changeContent(String content) throws BusinessException {
        validateContent(content);
        this.content = content;
    }

    public void changeStatus(PostStatus status) {
        this.status = status;
    }

}
