package br.com.home.api.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException notFoundException) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), notFoundException.getMessage(), new Date());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String defaultMessage = ex.getBindingResult().getAllErrors().stream().findFirst().get().getDefaultMessage();
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), defaultMessage, new Date());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
