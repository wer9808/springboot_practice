package com.example.post_crud.domain.post.service.dto.response;

import com.example.post_crud.domain.post.Post;

import java.time.Instant;

public record UpdatePostResponse(
        Long id,
        String title,
        String content,
        Instant createdAt,
        Instant updatedAt
) {
    public static UpdatePostResponse from(Post post) {
        return new UpdatePostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
