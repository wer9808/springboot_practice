package com.example.post_crud.domain.post.service.dto.response;

import com.example.post_crud.domain.post.repository.dto.PostListRow;

import java.util.List;

public record PostListResponse(
        List<PostResponse> posts
) {

    public static PostListResponse from(List<PostListRow> rows) {
        List<PostResponse> responses = rows.stream()
                .map(PostResponse::from)
                .toList();

        return new PostListResponse(responses);
    }

}
