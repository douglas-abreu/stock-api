package br.com.stock.exceptions;

import br.com.stock.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestLoginException.class)
    public ResponseEntity<ApiResponse<?>> requestLoginInvalid(RequestLoginException e) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ApiResponse<?>> tokenInvalid(TokenException e) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
