package com.example.article_crud.domain.auth;

import com.example.article_crud.common.security.dto.CurrentUserPrincipal;
import com.example.article_crud.domain.auth.dto.SignInRequest;
import com.example.article_crud.domain.auth.dto.SignInResponse;
import com.example.article_crud.domain.auth.dto.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal,
            @RequestBody SignUpRequest request
    ) {
        this.authService.signUp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(
            @AuthenticationPrincipal CurrentUserPrincipal currentUserPrincipal,
            @RequestBody SignInRequest request
    ) {
        SignInResponse response = this.authService.signIn(request);
        return ResponseEntity.ok(response);
    }

}
