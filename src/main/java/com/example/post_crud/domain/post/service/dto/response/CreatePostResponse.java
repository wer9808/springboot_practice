package com.example.post_crud.domain.post.service.dto.response;

import com.example.post_crud.domain.post.Post;

import java.time.Instant;

public record CreatePostResponse(
        Long id,
        String title,
        Instant createdAt
) {
    public static CreatePostResponse from(Post post) {
        return new CreatePostResponse(
                post.getId(),
                post.getTitle(),
                post.getCreatedAt()
        );
    }
}
