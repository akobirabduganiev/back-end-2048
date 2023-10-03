package dev.akobir.backend2048.exception;

public class AppBadRequestException extends RuntimeException {
    public AppBadRequestException(String message) {
        super(message);
    }
}
