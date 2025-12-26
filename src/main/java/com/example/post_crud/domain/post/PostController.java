package com.example.post_crud.domain.post;

import com.example.post_crud.common.security.dto.CurrentUserPrincipal;
import com.example.post_crud.domain.post.service.dto.response.PostListResponse;
import com.example.post_crud.domain.post.service.dto.response.PostResponse;
import com.example.post_crud.domain.post.service.PostService;
import com.example.post_crud.domain.post.service.dto.request.CreatePostRequest;
import com.example.post_crud.domain.post.service.dto.request.UpdatePostRequest;
import com.example.post_crud.domain.post.service.dto.response.CreatePostResponse;
import com.example.post_crud.domain.post.service.dto.response.UpdatePostResponse;
import com.example.post_crud.domain.user.dto.CurrentUserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<PostListResponse> getPosts(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal,
            @RequestParam(required = false) UUID userId
    ) {
        PostListResponse response;
        if (userId != null) {
            response = this.postService.findUserPosts(userId);
        }
        else {
            response = this.postService.findAllPosts();
        }
        return ResponseEntity
                .ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<List<PostResponse>> getMyPosts(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal
    ) {
        CurrentUserDto currentUserDto = CurrentUserDto.from(currentUserPrincipal);
        List<PostResponse> responses = this.postService.findMyPosts(currentUserDto);
        return ResponseEntity
                .ok(responses);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal,
            @PathVariable Long postId
    ) {
        PostResponse response = this.postService.findPost(postId);
        return ResponseEntity
                .ok(response);
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> postPost(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal,
            @RequestBody CreatePostRequest request
    ) {
        CurrentUserDto currentUserDto = CurrentUserDto.from(currentUserPrincipal);

        CreatePostResponse response = this.postService
                .createPost(request, currentUserDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<UpdatePostResponse> putPost(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal,
            @PathVariable Long postId,
            @RequestBody UpdatePostRequest request
    ) {
        CurrentUserDto currentUserDto = CurrentUserDto.from(currentUserPrincipal);

        UpdatePostResponse response = this.postService
                .updatePost(postId, request, currentUserDto);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal,
            @PathVariable Long postId
    ) {
        CurrentUserDto currentUserDto = CurrentUserDto.from(currentUserPrincipal);
        this.postService.removePost(postId, currentUserDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
