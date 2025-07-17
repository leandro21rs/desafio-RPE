package com.desafioSmileGo.infrastructure.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        // Valores de teste para secret e expiration
        String secret = "test-secret-key-for-testing-purposes-only";
        long expiration = 3600000L;
        jwtTokenProvider = new JwtTokenProvider(secret, expiration);
    }

    @Test
    void shouldGenerateTokenWithSuccess() {
        // Arrange
        String username = "testuser";

        // Act
        String token = jwtTokenProvider.generateToken(username);

        // Assert
        Assertions.assertNotNull(token);
        Assertions.assertFalse(token.isEmpty());
    }

    @Test
    void shouldValidateTokenWithSuccess() {
        // Arrange
        String username = "testuser";
        String token = jwtTokenProvider.generateToken(username);

        // Act
        boolean isValid = jwtTokenProvider.validateToken(token);

        // Assert
        Assertions.assertTrue(isValid);
    }

    @Test
    void shouldGetUsernameFromToken() {
        // Arrange
        String username = "testuser";
        String token = jwtTokenProvider.generateToken(username);

        // Act
        String extractedUsername = jwtTokenProvider.getUsername(token);

        // Assert
        Assertions.assertEquals(username, extractedUsername);
    }

    @Test
    void shouldReturnFalseForInvalidToken() {
        // Arrange
        String invalidToken = "invalid.token.here";

        // Act
        boolean isValid = jwtTokenProvider.validateToken(invalidToken);

        // Assert
        Assertions.assertFalse(isValid);
    }

    @Test
    void shouldReturnFalseForNullToken() {
        // Arrange
        String nullToken = null;

        // Act
        boolean isValid = jwtTokenProvider.validateToken(nullToken);

        // Assert
        Assertions.assertFalse(isValid);
    }

    @Test
    void shouldReturnFalseForEmptyToken() {
        // Arrange
        String emptyToken = "";

        // Act
        boolean isValid = jwtTokenProvider.validateToken(emptyToken);

        // Assert
        Assertions.assertFalse(isValid);
    }

    @Test
    void shouldGenerateTokensForDifferentUsernames() {
        // Arrange
        String username1 = "user1";
        String username2 = "user2";

        // Act
        String token1 = jwtTokenProvider.generateToken(username1);
        String token2 = jwtTokenProvider.generateToken(username2);

        // Assert
        Assertions.assertNotNull(token1);
        Assertions.assertNotNull(token2);
        Assertions.assertNotEquals(token1, token2);
    }

    @Test
    void shouldExtractUsernameFromValidToken() {
        // Arrange
        String username = "testuser";
        String token = jwtTokenProvider.generateToken(username);

        // Act
        String extractedUsername = jwtTokenProvider.getUsername(token);

        // Assert
        Assertions.assertEquals(username, extractedUsername);
    }

    @Test
    void shouldHandleNullUsername() {
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jwtTokenProvider.generateToken(null);
        });
    }

    @Test
    void shouldHandleEmptyUsername() {
        // Arrange
        String username = "";

        // Act
        String token = jwtTokenProvider.generateToken(username);

        // Assert
        Assertions.assertNotNull(token);
        Assertions.assertFalse(token.isEmpty());
    }
} 