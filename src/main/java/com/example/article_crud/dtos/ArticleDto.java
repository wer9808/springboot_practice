package com.example.article_crud.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class ArticleDto {

    private Long id;
    private UUID authorId;
    private String title;
    private String content;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public ArticleDto(Long id, UUID authorId, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        OffsetDateTime currentDateTime = OffsetDateTime.now();
        this.createdAt = currentDateTime;
        this.updatedAt = currentDateTime;
    }

}


