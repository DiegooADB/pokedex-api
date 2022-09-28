package me.diego.pokedex.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.web.context.request.WebRequest;

@Data
@SuperBuilder
public class ExceptionDetails {
    protected String title;
    protected int status;
    protected String details;
    protected String timestamp;
    protected String path;
}

