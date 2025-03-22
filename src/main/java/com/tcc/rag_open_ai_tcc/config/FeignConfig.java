package com.tcc.rag_open_ai_tcc.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public interface FeignConfig {

    @Bean
    default RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // Log or print the headers
                System.out.println("Headers: " + template.headers());
            }
        };
    }
}