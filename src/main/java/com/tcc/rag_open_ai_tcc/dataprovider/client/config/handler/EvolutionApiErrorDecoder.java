package com.tcc.rag_open_ai_tcc.dataprovider.client.config.handler;

import com.tcc.rag_open_ai_tcc.exceptions.EvolutionApiException;
import feign.codec.ErrorDecoder;
import feign.Response;
import org.springframework.http.HttpStatus;

public class EvolutionApiErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == 400) {
            return new EvolutionApiException("Bad Request", HttpStatus.BAD_REQUEST);
        }

        if (response.status() == 401) {
            return new EvolutionApiException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        if (response.status() == 403) {
            return new EvolutionApiException("Forbidden", HttpStatus.FORBIDDEN);
        }

        if (response.status() == 404) {
            return new EvolutionApiException("Not Found", HttpStatus.NOT_FOUND);
        }

        if (response.status() == 500) {
            return new EvolutionApiException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new EvolutionApiException("Generic Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
