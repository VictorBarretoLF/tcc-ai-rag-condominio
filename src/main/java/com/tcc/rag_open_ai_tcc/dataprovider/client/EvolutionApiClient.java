package com.tcc.rag_open_ai_tcc.dataprovider.client;

import com.tcc.rag_open_ai_tcc.dataprovider.client.config.EvolutionApiClientConfig;
import com.tcc.rag_open_ai_tcc.dto.EvolutionApiMessageRequest;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "evolution-api",
        url = "${evolution-api.url}",
        configuration = EvolutionApiClientConfig.class
)
public interface EvolutionApiClient {

    @PostMapping(value = "/message/sendText/{instanceName}")
    @Retry(name = "EvolutionApiClient")
    Object sendMessage(@PathVariable("instanceName") final String instanceName,
                       @RequestBody final EvolutionApiMessageRequest message);

}
