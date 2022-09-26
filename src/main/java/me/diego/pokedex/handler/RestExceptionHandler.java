package me.diego.pokedex.handler;

import me.diego.pokedex.exception.*;
import me.diego.pokedex.utils.DateUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private final DateUtil dateUtil;

    public RestExceptionHandler(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException bre) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BadRequestExceptionDetails.builder()
                        .timestamp(dateUtil.dateTimeFormatter(LocalDateTime.now()))
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, check the documentation")
                        .details(bre.getMessage())
                        .developerMessage(bre.getClass().getName())
                        .build()
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ConflictExceptionDetails> handleConflictException(ConflictException ce) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ConflictExceptionDetails.builder()
                        .timestamp(dateUtil.dateTimeFormatter(LocalDateTime.now()))
                        .status(HttpStatus.CONFLICT.value())
                        .title("Conflict, " + ce.getType() + " already exists")
                        .details(ce.getMessage())
                        .developerMessage(ce.getClass().getName())
                        .build()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BadCredentialsExceptionDetails> handleBadCredentialsException(BadCredentialsException bce) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                BadCredentialsExceptionDetails.builder()
                        .timestamp(dateUtil.dateTimeFormatter(LocalDateTime.now()))
                        .status(HttpStatus.CONFLICT.value())
                        .title("Password or user is incorrect")
                        .details(bce.getMessage())
                        .developerMessage(bce.getClass().getName())
                        .build()
        );

    }
}
