package com.example.post_crud.domain.post.service;

import com.example.post_crud.common.exception.ApiErrorCode;
import com.example.post_crud.domain.post.Post;
import com.example.post_crud.domain.post.PostStatus;
import com.example.post_crud.common.exception.service.BusinessException;
import com.example.post_crud.domain.post.repository.PostRepository;
import com.example.post_crud.domain.post.repository.dto.PostListRow;
import com.example.post_crud.domain.post.service.dto.response.PostListResponse;
import com.example.post_crud.domain.post.service.dto.response.PostResponse;
import com.example.post_crud.domain.post.service.dto.request.CreatePostRequest;
import com.example.post_crud.domain.post.service.dto.request.UpdatePostRequest;
import com.example.post_crud.domain.post.service.dto.response.CreatePostResponse;
import com.example.post_crud.domain.post.service.dto.response.UpdatePostResponse;
import com.example.post_crud.domain.user.dto.CurrentUserDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    private void checkPostUpdatePermission(UUID userId, Post post) throws BusinessException {
        if (!post.getAuthorId().equals(userId)) {
            throw new BusinessException(ApiErrorCode.PERMISSION_ACCESS_DENIED);
        }
    }

    @Transactional
    public PostListResponse findAllPosts() {
        List<PostListRow> rows = this.postRepository
                .findActivePostList();
        return PostListResponse.from(rows);
    }

    @Transactional
    public List<PostResponse> findMyPosts(CurrentUserDto currentUserDto) {
        return this.postRepository
                .findPostListByAuthorId(currentUserDto.id())
                .stream().map(PostResponse::from)
                .toList();
    }

    @Transactional
    public PostListResponse findUserPosts(UUID userId) {
        List<PostListRow> rows = this.postRepository
                .findActivePostListByAuthorId(userId);
        return PostListResponse.from(rows);
    }

    @Transactional
    public PostResponse findPost(Long postId) throws BusinessException {
        PostListRow row = this.postRepository
                .findActivePostListRowById(postId)
                .orElseThrow(() -> new BusinessException(ApiErrorCode.POST_NOT_FOUND));
        return PostResponse.from(row);
    }

    @Transactional
    public CreatePostResponse createPost(
            CreatePostRequest request,
            CurrentUserDto currentUserDto
    ) throws BusinessException {
        Post post = Post.of(currentUserDto.id(), request.title(), request.content());
        this.postRepository.save(post);
        return CreatePostResponse.from(post);
    }

    @Transactional
    public UpdatePostResponse updatePost(
            Long postId,
            UpdatePostRequest request,
            CurrentUserDto currentUserDto
    ) throws BusinessException {
        Post post = this.postRepository
                .findById(postId)
                .orElseThrow(() -> new BusinessException(ApiErrorCode.POST_NOT_FOUND));
        checkPostUpdatePermission(currentUserDto.id(), post);
        post.changeTitle(request.title());
        post.changeContent(request.content());
        return UpdatePostResponse.from(post);
    }

    @Transactional
    public void removePost(
            Long postId,
            CurrentUserDto currentUserDto
    ) throws BusinessException {
        Post post = this.postRepository
                .findById(postId)
                .orElseThrow(() -> new BusinessException(ApiErrorCode.POST_NOT_FOUND));
        checkPostUpdatePermission(currentUserDto.id(), post);
        post.changeStatus(PostStatus.INACTIVE);
    }

}
