package me.diego.pokedex.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@Getter
public class ConflictException extends RuntimeException {
    private final String type;

    public ConflictException(String message, String type) {
        super(message);
        this.type = type;
    }
}
