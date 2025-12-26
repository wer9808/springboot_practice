package com.example.post_crud.domain.post.service.dto.request;

public record UpdatePostRequest(
        String title,
        String content
) { }
