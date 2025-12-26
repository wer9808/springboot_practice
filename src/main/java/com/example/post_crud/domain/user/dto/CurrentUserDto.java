package com.example.post_crud.domain.user.dto;

import com.example.post_crud.common.security.dto.CurrentUserPrincipal;

import java.time.Instant;
import java.util.UUID;

public record CurrentUserDto(
        UUID id
) {

    public static CurrentUserDto from(CurrentUserPrincipal currentUserPrincipal) {
        return new CurrentUserDto(
                currentUserPrincipal.id()
        );
    }
}
