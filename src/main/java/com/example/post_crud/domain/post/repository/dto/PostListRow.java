package com.example.post_crud.domain.post.repository.dto;

import com.example.post_crud.domain.post.PostStatus;

import java.time.Instant;
import java.util.UUID;

public record PostListRow(
        Long id,
        UUID authorId,
        String authorName,
        String title,
        String content,
        Instant createdAt,
        Instant updatedAt,
        PostStatus status
) {
}
