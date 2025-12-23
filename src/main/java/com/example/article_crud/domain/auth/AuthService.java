package com.example.article_crud.domain.auth;

import com.example.article_crud.common.exception.service.BusinessException;
import com.example.article_crud.common.exception.ApiErrorCode;
import com.example.article_crud.common.security.JwtUtil;
import com.example.article_crud.domain.auth.dto.SignInRequest;
import com.example.article_crud.domain.auth.dto.SignInResponse;
import com.example.article_crud.domain.auth.dto.SignUpRequest;
import com.example.article_crud.domain.auth.dto.SignUpResponse;
import com.example.article_crud.domain.user.User;
import com.example.article_crud.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final LocalCredentialRepository localCredentialRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final long jwtExpirationSec = 60 * 60;

    public AuthService(
            UserRepository userRepository,
            LocalCredentialRepository localCredentialRepository,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.localCredentialRepository = localCredentialRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {

        LocalCredential localCredential = localCredentialRepository
                .findByEmail(request.email())
                .orElse(null);

        if (localCredential != null) {
            throw new BusinessException(ApiErrorCode.AUTH_EMAIL_DUPLICATED);
        }

        String passwordHash = passwordEncoder.encode(request.password());

        User user = User.of(request.username());
        this.userRepository.save(user);

        localCredential = LocalCredential.of(user.getId(), request.email(), passwordHash);
        this.localCredentialRepository.save(localCredential);

        return new SignUpResponse(user.getId());
    }

    @Transactional
    public SignInResponse signIn(SignInRequest request) throws BusinessException {
        LocalCredential localCredential = localCredentialRepository
                .findByEmailAndIsActiveTrue(request.email())
                .orElse(null);

        if (localCredential == null) {
            throw new BusinessException(ApiErrorCode.AUTH_INVALID_CREDENTIAL);
        }

        if (!passwordEncoder.matches(request.password(), localCredential.getPasswordHash())) {
            throw new BusinessException(ApiErrorCode.AUTH_INVALID_CREDENTIAL);
        }

        String accessToken = this.jwtUtil.build(localCredential.getUserId(), jwtExpirationSec);

        return new SignInResponse(accessToken);
    }

}
