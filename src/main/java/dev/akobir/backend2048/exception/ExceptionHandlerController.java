package dev.akobir.backend2048.exception;

import dev.akobir.backend2048.exception.dto.ErrorMessage;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            UserBadRequestException.class, AppBadRequestException.class})
    public ResponseEntity<ErrorMessage> handleBadRequestException(RuntimeException e) {
        var errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        errorMessage.setStatus(HttpStatus.BAD_REQUEST);
        errorMessage.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorMessage.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedExceptionHandler(AccessDeniedException e, HttpServletRequest request) {
        var errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        errorMessage.setStatus(HttpStatus.FORBIDDEN);
        errorMessage.setStatusCode(HttpStatus.FORBIDDEN.value());
        errorMessage.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorMessage> expiredJwtExceptionHandler(ExpiredJwtException e, HttpServletRequest request) {
        var errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        errorMessage.setStatus(HttpStatus.UNAUTHORIZED);
        errorMessage.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorMessage.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

}