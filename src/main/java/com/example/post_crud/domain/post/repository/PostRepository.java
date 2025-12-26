package com.example.post_crud.domain.post.repository;

import com.example.post_crud.domain.post.Post;
import com.example.post_crud.domain.post.repository.dto.PostListRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
    select new com.example.post_crud.domain.post.repository.dto.PostListRow(
        a.id,
        a.authorId,
        u.username,
        a.title,
        a.content,
        a.createdAt,
        a.updatedAt,
        a.status
    )
    from Post a
    join User u on u.id = a.authorId
    where a.authorId = :authorId
    order by a.createdAt desc
""")
    List<PostListRow> findPostListByAuthorId(UUID authorId);

    @Query("""
    select new com.example.post_crud.domain.post.repository.dto.PostListRow(
        a.id,
        a.authorId,
        u.username,
        a.title,
        a.content,
        a.createdAt,
        a.updatedAt,
        a.status
    )
    from Post a
    join User u on u.id = a.authorId
    where a.authorId = :authorId
    and a.status = 'ACTIVE'
    order by a.createdAt desc
""")
    List<PostListRow> findActivePostListByAuthorId(UUID authorId);

    @Query("""
    select new com.example.post_crud.domain.post.repository.dto.PostListRow(
        a.id,
        a.authorId,
        u.username,
        a.title,
        a.content,
        a.createdAt,
        a.updatedAt,
        a.status
    )
    from Post a
    join User u on u.id = a.authorId
    where a.status = 'ACTIVE'
    order by a.createdAt desc
""")
    List<PostListRow> findActivePostList();

    @Query("""
    select new com.example.post_crud.domain.post.repository.dto.PostListRow(
        a.id,
        a.authorId,
        u.username,
        a.title,
        a.content,
        a.createdAt,
        a.updatedAt,
        a.status
    )
    from Post a
    join User u on u.id = a.authorId
    where a.id = :postId
    order by a.createdAt desc
""")
    Optional<PostListRow> findPostListRowById(Long postId);

    @Query("""
    select new com.example.post_crud.domain.post.repository.dto.PostListRow(
        a.id,
        a.authorId,
        u.username,
        a.title,
        a.content,
        a.createdAt,
        a.updatedAt,
        a.status
    )
    from Post a
    join User u on u.id = a.authorId
    where a.id = :postId
    and a.status = 'ACTIVE'
    order by a.createdAt desc
""")
    Optional<PostListRow> findActivePostListRowById(Long postId);

}
