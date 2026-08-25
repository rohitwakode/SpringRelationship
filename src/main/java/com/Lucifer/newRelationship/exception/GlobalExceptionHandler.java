package com.Lucifer.newRelationship.exception;

import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmail.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateEmail(DuplicateEmail e , HttpServletRequest req) {
        ErrorResponseDto errorResponse= new  ErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                e.getMessage(),
                req.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(StudentNotFound.class)
    public ResponseEntity<ErrorResponseDto> handleStudentNotFound(StudentNotFound e,HttpServletRequest req) {
        ErrorResponseDto errorResponse= new  ErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                req.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFound(ResourceNotFound e,HttpServletRequest req) {
        ErrorResponseDto errorResponse= new  ErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                req.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<ErrorResponseDto> handleOptimisticException(Exception e, HttpServletRequest req) {
        ErrorResponseDto errorResponse= new  ErrorResponseDto(

                LocalDateTime.now(),
                HttpStatus.CONTINUE.value(),
                "student was already updated by another user",
             null,
                req.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }
    //exception for validation

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest req) {

        Map<String,String> errors = new HashMap<>();
        e.getBindingResult()
                .getFieldErrors()
                .forEach((f)->{
                    errors.put(f.getField(),f.getDefaultMessage());
                }
                );
        ErrorResponseDto errorResponse= new  ErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errors,
                req.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
}
