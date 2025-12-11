package com.example.article_crud.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ArticleDto {

    private Long id;
    private UUID writerId;
    private String title;
    private String content;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public ArticleDto(Long id, UUID writerId, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        OffsetDateTime currentDateTime = OffsetDateTime.now();
        this.createdAt = currentDateTime;
        this.updatedAt = currentDateTime;
    }

}


