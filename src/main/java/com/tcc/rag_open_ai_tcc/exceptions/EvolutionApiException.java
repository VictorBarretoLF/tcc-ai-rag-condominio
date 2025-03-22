package com.tcc.rag_open_ai_tcc.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EvolutionApiException extends RuntimeException {
    private HttpStatus status;

    public EvolutionApiException(String message) {
        super(message);
    }

    public EvolutionApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
