package com.rohitvpatil0810.v2data.common.security;

import com.rohitvpatil0810.v2data.common.api.exceptions.ApiException;
import com.rohitvpatil0810.v2data.common.api.exceptions.ForbiddenException;
import com.rohitvpatil0810.v2data.common.api.responses.ApiResponse;
import com.rohitvpatil0810.v2data.modules.auth.service.DBUserDetailsService;
import com.rohitvpatil0810.v2data.modules.users.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final DBUserDetailsService dbUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                Claims claims = jwtUtil.verifyAccessToken(token);

                User user = (User) dbUserDetailsService.loadUserByUsername(claims.getSubject());

                if (!user.isEnabled()) {
                    log.debug("User {} is not active (disabled/locked/expired)", user.getEmail());
                    throw new ForbiddenException("User account is not active");
                }

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        null
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (ApiException ex) {
            ApiResponse res = ApiException.handle(ex);
            res.writeToResponse(response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
