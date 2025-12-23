package com.example.article_crud.domain.article.repository;

import com.example.article_crud.domain.article.Article;
import com.example.article_crud.domain.article.repository.dto.ArticleListRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("""
    select new com.example.article_crud.domain.article.repository.dto.ArticleListRow(
        a.id,
        a.authorId,
        u.username,
        a.title,
        a.content,
        a.createdAt,
        a.updatedAt,
        a.status
    )
    from Article a
    join User u on u.id = a.authorId
    where a.authorId = :authorId
    order by a.createdAt desc
""")
    List<ArticleListRow> findArticleListByAuthorId(UUID authorId);

    @Query("""
    select new com.example.article_crud.domain.article.repository.dto.ArticleListRow(
        a.id,
        a.authorId,
        u.username,
        a.title,
        a.content,
        a.createdAt,
        a.updatedAt,
        a.status
    )
    from Article a
    join User u on u.id = a.authorId
    where a.authorId = :authorId
    and a.status = 'ACTIVE'
    order by a.createdAt desc
""")
    List<ArticleListRow> findActiveArticleListByAuthorId(UUID authorId);

    @Query("""
    select new com.example.article_crud.domain.article.repository.dto.ArticleListRow(
        a.id,
        a.authorId,
        u.username,
        a.title,
        a.content,
        a.createdAt,
        a.updatedAt,
        a.status
    )
    from Article a
    join User u on u.id = a.authorId
    where a.status = 'ACTIVE'
    order by a.createdAt desc
""")
    List<ArticleListRow> findActiveArticleList();

    @Query("""
    select new com.example.article_crud.domain.article.repository.dto.ArticleListRow(
        a.id,
        a.authorId,
        u.username,
        a.title,
        a.content,
        a.createdAt,
        a.updatedAt,
        a.status
    )
    from Article a
    join User u on u.id = a.authorId
    where a.id = :articleId
    order by a.createdAt desc
""")
    Optional<ArticleListRow> findArticleListRowById(Long articleId);

    @Query("""
    select new com.example.article_crud.domain.article.repository.dto.ArticleListRow(
        a.id,
        a.authorId,
        u.username,
        a.title,
        a.content,
        a.createdAt,
        a.updatedAt,
        a.status
    )
    from Article a
    join User u on u.id = a.authorId
    where a.id = :articleId
    and a.status = 'ACTIVE'
    order by a.createdAt desc
""")
    Optional<ArticleListRow> findActiveArticleListRowById(Long articleId);

}
