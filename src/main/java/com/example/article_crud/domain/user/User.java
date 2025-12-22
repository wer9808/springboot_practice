package com.example.article_crud.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "createdAt", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updatedAt", nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;

    public User(String username) {
        this.username = username;
    }

}
