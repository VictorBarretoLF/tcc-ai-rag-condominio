package com.tcc.rag_open_ai_tcc.dataprovider.client.config;

import com.tcc.rag_open_ai_tcc.config.FeignConfig;
import com.tcc.rag_open_ai_tcc.dataprovider.client.config.handler.EvolutionApiErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Retryer;

@Configuration
public class EvolutionApiClientConfig implements FeignConfig {

    @Value("${evolution-api.key}")
    private String apiKey;

    @Bean
    public RequestInterceptor apiKeyInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Content-Type", "application/json");
            requestTemplate.header("apikey", apiKey);
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new EvolutionApiErrorDecoder();
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(3000, 4000, 3);
    }

}
