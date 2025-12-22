package com.example.article_crud.common.security;

import com.example.article_crud.common.exception.ApiErrorCode;
import com.example.article_crud.common.exception.dto.CommonErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ApiErrorCode errorCode = ApiErrorCode.AUTH_INVALID_TOKEN;

        CommonErrorResponse errorResponse = CommonErrorResponse.from(errorCode);

        response.setStatus(errorCode.getHttpStatusCode());
        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
