package com.example.post_crud.domain.post.service.dto.response;

import com.example.post_crud.domain.post.PostStatus;
import com.example.post_crud.domain.post.repository.dto.PostListRow;

import java.time.Instant;
import java.util.UUID;

public record PostResponse(
        Long id,
        UUID authorId,
        String authorName,
        String title,
        String content,
        Instant createdAt,
        Instant updatedAt,
        PostStatus status
) {

    public static PostResponse from(PostListRow row) {
        return new PostResponse(
                row.id(),
                row.authorId(),
                row.authorName(),
                row.title(),
                row.content(),
                row.createdAt(),
                row.updatedAt(),
                row.status()
        );
    }

}


