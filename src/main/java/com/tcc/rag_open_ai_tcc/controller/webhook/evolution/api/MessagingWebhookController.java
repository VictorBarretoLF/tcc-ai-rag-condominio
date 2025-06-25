package com.tcc.rag_open_ai_tcc.controller.webhook.evolution.api;

import com.tcc.rag_open_ai_tcc.dto.EvolutionApiPayload;
import com.tcc.rag_open_ai_tcc.service.v1.MessageReceivedProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessagingWebhookController {

    private final MessageReceivedProcessor messageReceivedProcessor;

    @PostMapping("/messages-upsert")
    public void notifiesWhenMessageIsReceived(@RequestBody EvolutionApiPayload payload) {
        System.out.println("Webhook test4 received GET");
        messageReceivedProcessor.processIncomingMessage(payload);
    }

    @GetMapping("/send-message")
    public void test() {
        System.out.println("Webhook test4 received GET");
        System.out.println("Webhook received /send-message GET");
    }

    @GetMapping
    public ResponseEntity<Void> test4(@RequestBody EvolutionApiPayload test) {
        System.out.println("Webhook test4 received GET");
        System.out.println(test.getEvent());
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> test5() {
        System.out.println("Webhook test5 received POST");
        return ResponseEntity.ok().build();
    }

}
