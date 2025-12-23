package com.example.article_crud.domain.auth;

import com.example.article_crud.common.security.dto.CurrentUserPrincipal;
import com.example.article_crud.domain.auth.dto.SignInRequest;
import com.example.article_crud.domain.auth.dto.SignInResponse;
import com.example.article_crud.domain.auth.dto.SignUpRequest;
import com.example.article_crud.domain.auth.dto.SignUpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(
            @RequestBody SignUpRequest request
    ) {
        SignUpResponse response = this.authService.signUp(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(
            @RequestBody SignInRequest request
    ) {
        SignInResponse response = this.authService.signIn(request);
        return ResponseEntity.ok(response);
    }

}
