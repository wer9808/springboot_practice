package com.example.article_crud.common.security.filter;

import com.example.article_crud.common.security.JwtUtil;
import com.example.article_crud.common.security.dto.CurrentUserPrincipal;
import com.example.article_crud.domain.auth.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            Jws<Claims> jws = jwtUtil.verify(token);
            Claims claims = jws.getPayload();

            UUID userId = UUID.fromString(claims.getSubject());
            CurrentUserPrincipal currentUserPrincipal = new CurrentUserPrincipal(userId);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    currentUserPrincipal,
                    null,
                    null
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            response.sendError(401, "Invalid Token");
        }

        filterChain.doFilter(request, response);
    }


}
