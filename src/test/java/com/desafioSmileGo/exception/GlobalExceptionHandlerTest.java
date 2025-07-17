package com.desafioSmileGo.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldHandleNotFoundException() {
        // Arrange
        NotFoundException exception = new NotFoundException("Resource not found");

        // Act
        ResponseEntity<String> response = globalExceptionHandler.handleNotFoundException(exception);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Resource not found", response.getBody());
    }

    @Test
    void shouldHandleRetryTestException() {
        // Arrange
        RetryTestException exception = new RetryTestException("Retry test error");

        // Act
        ResponseEntity<String> response = globalExceptionHandler.handleRetryTestException(exception);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Retry test error", response.getBody());
    }

    @Test
    void shouldHandleNotFoundExceptionWithNullMessage() {
        // Arrange
        NotFoundException exception = new NotFoundException(null);

        // Act
        ResponseEntity<String> response = globalExceptionHandler.handleNotFoundException(exception);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void shouldHandleRetryTestExceptionWithNullMessage() {
        // Arrange
        RetryTestException exception = new RetryTestException(null);

        // Act
        ResponseEntity<String> response = globalExceptionHandler.handleRetryTestException(exception);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void shouldHandleNotFoundExceptionWithEmptyMessage() {
        // Arrange
        NotFoundException exception = new NotFoundException("");

        // Act
        ResponseEntity<String> response = globalExceptionHandler.handleNotFoundException(exception);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("", response.getBody());
    }

    @Test
    void shouldHandleRetryTestExceptionWithEmptyMessage() {
        // Arrange
        RetryTestException exception = new RetryTestException("");

        // Act
        ResponseEntity<String> response = globalExceptionHandler.handleRetryTestException(exception);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("", response.getBody());
    }
} 