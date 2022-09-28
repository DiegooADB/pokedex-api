package me.diego.pokedex.handler;

import me.diego.pokedex.exception.*;
import me.diego.pokedex.utils.DateUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private final DateUtil dateUtil;

    public RestExceptionHandler(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException bre, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BadRequestExceptionDetails.builder()
                        .timestamp(dateUtil.dateTimeFormatter(LocalDateTime.now()))
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception")
                        .details(bre.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ConflictExceptionDetails> handleConflictException(ConflictException ce, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ConflictExceptionDetails.builder()
                        .timestamp(dateUtil.dateTimeFormatter(LocalDateTime.now()))
                        .status(HttpStatus.CONFLICT.value())
                        .title("Conflict Exception")
                        .details(ce.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BadCredentialsExceptionDetails> handleBadCredentialsException(BadCredentialsException bce, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                BadCredentialsExceptionDetails.builder()
                        .timestamp(dateUtil.dateTimeFormatter(LocalDateTime.now()))
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .title("Password or user is incorrect")
                        .details(bce.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ForbiddenExceptionDetails> handleForbiddenException(AccessDeniedException ade, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ForbiddenExceptionDetails.builder()
                .timestamp(dateUtil.dateTimeFormatter(LocalDateTime.now()))
                .status(HttpStatus.FORBIDDEN.value())
                .title("Access denied")
                .details("Without permission")
                .path(request.getRequestURI())
                .build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String[] url = request.getDescription(false).split("=");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BadRequestExceptionDetails.builder()
                        .timestamp(dateUtil.dateTimeFormatter(LocalDateTime.now()))
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception")
                        .details(ex.getFieldError().getDefaultMessage())
                        .path(url[1])
                        .build()
        );
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String[] url = request.getDescription(false).split("=");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BadRequestExceptionDetails.builder()
                        .timestamp(dateUtil.dateTimeFormatter(LocalDateTime.now()))
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception")
                        .details(ex.getMessage())
                        .path(url[1])
                        .build()
        );
    }
}
