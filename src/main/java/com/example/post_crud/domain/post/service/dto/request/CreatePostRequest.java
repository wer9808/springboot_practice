package com.example.post_crud.domain.post.service.dto.request;

public record CreatePostRequest(
    String title,
    String content
) {}

