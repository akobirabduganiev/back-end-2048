package dev.akobir.backend2048.exception;

import dev.akobir.backend2048.exception.dto.ErrorMessage;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerController {
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
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

}