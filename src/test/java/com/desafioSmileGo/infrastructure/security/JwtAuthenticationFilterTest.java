package com.desafioSmileGo.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.context.SecurityContextHolder;

class JwtAuthenticationFilterTest {

    private JwtTokenProvider tokenProvider;
    private JwtAuthenticationFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        tokenProvider = Mockito.mock(JwtTokenProvider.class);
        filter = new JwtAuthenticationFilter(tokenProvider);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        filterChain = Mockito.mock(FilterChain.class);
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldSetAuthenticationWhenValidTokenIsPresent() throws Exception {
        String token = "valid.jwt.token";
        String username = "testUser";

        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        Mockito.when(tokenProvider.validateToken(token)).thenReturn(true);
        Mockito.when(tokenProvider.getUsername(token)).thenReturn(username);

        filter.doFilterInternal(request, response, filterChain);

        Assertions.assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        Assertions.assertEquals(username, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Mockito.verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldNotSetAuthenticationWhenAuthorizationHeaderIsMissing() throws Exception {
        Mockito.when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());
        Mockito.verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldNotSetAuthenticationWhenTokenIsInvalid() throws Exception {
        String token = "invalid.jwt.token";

        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        Mockito.when(tokenProvider.validateToken(token)).thenReturn(false);

        filter.doFilterInternal(request, response, filterChain);

        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());
        Mockito.verify(filterChain).doFilter(request, response);
    }
}
