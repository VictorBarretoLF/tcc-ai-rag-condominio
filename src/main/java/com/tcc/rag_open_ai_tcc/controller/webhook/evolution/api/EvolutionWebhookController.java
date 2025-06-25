package com.tcc.rag_open_ai_tcc.controller.webhook.evolution.api;

import com.tcc.rag_open_ai_tcc.dto.EvolutionApiPayload;
import com.tcc.rag_open_ai_tcc.service.v1.MessageReceivedProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EvolutionWebhookController {

    private final MessageReceivedProcessor messageReceivedProcessor;

    @PostMapping("/contacts-update")
    public ResponseEntity<Void> onContactsUpdate(@RequestBody EvolutionApiPayload payload) {
        // trate o evento de atualização de contatos
        return ResponseEntity.ok().build();
    }

    @PostMapping("/chats-upsert")
    public ResponseEntity<Void> onChatsUpsert(@RequestBody EvolutionApiPayload payload) {
        // trate o upsert de conversas
        return ResponseEntity.ok().build();
    }

    @PostMapping("/messages-upsert")
    public ResponseEntity<Void> onMessagesUpsert(@RequestBody EvolutionApiPayload payload) {
        // já existente: trate mensagens recebidas
        messageReceivedProcessor.processIncomingMessage(payload);
        return ResponseEntity.ok().build();
    }

    // Se quiser cobrir outros eventos:
    @PostMapping("/chats-update")
    public ResponseEntity<Void> onChatsUpdate(@RequestBody EvolutionApiPayload payload) {
        return ResponseEntity.ok().build();
    }
}
