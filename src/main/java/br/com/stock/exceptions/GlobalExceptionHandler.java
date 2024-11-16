package br.com.stock.exceptions;

import br.com.stock.model.ApiResponse;
import br.com.stock.util.Constants;
import br.com.stock.util.MsgSystem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestLoginException.class)
    public ResponseEntity<ApiResponse<?>> requestLoginInvalid(RequestLoginException e) {
        ApiResponse<?> response = new ApiResponse<>();
        response.of(HttpStatus.NOT_FOUND, MsgSystem.errGet(Constants.CATEGORIA));

        return ResponseEntity.status(response.getStatus()).body(response);
    }


}
