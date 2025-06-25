package com.tcc.rag_open_ai_tcc.service.v1.impl;

import com.tcc.rag_open_ai_tcc.dataprovider.client.EvolutionApiClient;
import com.tcc.rag_open_ai_tcc.dto.ChatRequest;
import com.tcc.rag_open_ai_tcc.dto.EvolutionApiMessageRequest;
import com.tcc.rag_open_ai_tcc.dto.EvolutionApiPayload;
import com.tcc.rag_open_ai_tcc.service.v1.GenAIService;
import com.tcc.rag_open_ai_tcc.service.v1.MessageReceivedProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageReceivedProcessorImpl implements MessageReceivedProcessor {

    private final GenAIService genAIService;
    private final EvolutionApiClient evolutionApiClient;

    @Override
    public void processIncomingMessage(EvolutionApiPayload payload) {
        log.info("Webhook received messages-upsert POST. Payload: {}", payload);

        final var remoteJid = payload.getData().getKey().getRemoteJid();
        final var pushName = payload.getData().getPushName();
        final var messageType = payload.getData().getMessageType();
        final var conversation = payload.getData().getMessage().getConversation();
        final var instance = payload.getInstance();

        if (isSafeRemoteJid(remoteJid)) {
            log.info("Received message from safe host. Remote JID: {}, Push Name: {}, Message Type: {}, Conversation: {}",
                    remoteJid, pushName, messageType, conversation);

//            final var aiResponse = genAIService.getResponse(remoteJid, conversation);
            final var chatRequest = new ChatRequest(
                    conversation,
                    remoteJid
            );
            final var aiResponse = genAIService.getRagContextResponse(chatRequest, UUID.fromString("3091d278-ebf5-4085-9308-116160952e91"), 10);
            // http://localhost:8081/api/v2/chat/contextual?fileUuid=3091d278-ebf5-4085-9308-116160952e91
            final var evolutionApiMessageRequestBody = new EvolutionApiMessageRequest(remoteJid, aiResponse);
            final var response = evolutionApiClient.sendMessage(instance, evolutionApiMessageRequestBody);
            log.info("Evolution API response: {}", response);
            return;
        }

        log.info("Received message from unsafe host. Remote JID: {}, Push Name: {}, Message Type: {}, Conversation: {}",
                remoteJid, pushName, messageType, conversation);
    }

    private boolean isSafeRemoteJid(String remoteJid) {
        return true;
    }

}
