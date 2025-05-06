package com.group05.exceptionHandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.group05.exceptionHandler.exceptions.InsufficientStockException;
import com.group05.exceptionHandler.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handleConflictException(ResourceNotFoundException resourceNotFoundException, HttpServletRequest request){
        return new ResponseEntity<>(
            new ErrorDetails(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                resourceNotFoundException.getMessage(),
                request.getRequestURI()
            ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request){
        return new ResponseEntity<>(
            new ErrorDetails(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                ex.getMessage(),
                request.getRequestURI()
            ), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handleUsernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request){
        return new ResponseEntity<>(
            new ErrorDetails(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                ex.getMessage(),
                request.getRequestURI()
            ), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handleInsufficientStockException(InsufficientStockException ex, HttpServletRequest request){
        return new ResponseEntity<>(
            new ErrorDetails(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Insufficient stock",
                ex.getMessage(),
                request.getRequestURI()
            ), HttpStatus.BAD_REQUEST
        );
    }

}
