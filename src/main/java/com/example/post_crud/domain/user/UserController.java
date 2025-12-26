package com.example.post_crud.domain.user;

import com.example.post_crud.common.security.dto.CurrentUserPrincipal;
import com.example.post_crud.domain.user.dto.CurrentUserDto;
import com.example.post_crud.domain.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyInfo(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal
    ) {
        CurrentUserDto currentUserDto = CurrentUserDto.from(currentUserPrincipal);
        UserResponse userResponse = this.userService.findCurrentUser(currentUserDto);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal,
            @PathVariable UUID userId
    ) {
        CurrentUserDto currentUserDto = CurrentUserDto.from(currentUserPrincipal);
        UserResponse userResponse = this.userService.findUser(userId, currentUserDto);
        return ResponseEntity.ok(userResponse);
    }
}
