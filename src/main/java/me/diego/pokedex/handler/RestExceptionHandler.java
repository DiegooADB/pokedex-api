package me.diego.pokedex.handler;

import lombok.RequiredArgsConstructor;
import me.diego.pokedex.exception.BadRequestException;
import me.diego.pokedex.exception.BadRequestExceptionDetails;
import me.diego.pokedex.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private final DateUtil dateUtil;

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
}
