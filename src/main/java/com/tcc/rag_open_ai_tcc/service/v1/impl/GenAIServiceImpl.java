package com.tcc.rag_open_ai_tcc.service.v1.impl;

import com.tcc.rag_open_ai_tcc.dto.ChatRequest;
import com.tcc.rag_open_ai_tcc.service.v1.Assistant;
import com.tcc.rag_open_ai_tcc.service.v1.GenAIService;
import com.tcc.rag_open_ai_tcc.service.v1.RAGAssistantInMemory;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

@Service
@RequiredArgsConstructor
public class GenAIServiceImpl implements GenAIService {

    private final Assistant assistant;
    private final RAGAssistantInMemory ragAssistantInMemory;
    private final VectorSearchService vectorSearchService;

    @Override
    public String getResponse(ChatRequest request) {
        return assistant.chat(request.userId(), request.question());
    }

    @Override
    public String getResponse(String id, String message) {
        return assistant.chat(id, message);
    }

    public String getResponseSimple(ChatRequest request) {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(UserMessage.userMessage(request.question()));
        var model = OpenAiChatModel.builder()
                .apiKey("demo")
                .modelName(GPT_4_O_MINI)
                .build();

        return model.generate(messages).content().text();
    }

    @Override
    public String getRagContextResponse(ChatRequest request, UUID fileId, int limit) {
        String context = vectorSearchService.findRelevantContext(request.question(), fileId, limit);
        String formattedPrompt = formatPrompt(request.question(), context);

        return OpenAiChatModel.builder()
                .apiKey("demo")
                .modelName(GPT_4_O_MINI)
                .build()
                .generate(formattedPrompt);
    }

    public String getRagContextResponseV2(ChatRequest request, UUID fileId, int limit) {
        String context = vectorSearchService.findRelevantContext(request.question(), fileId, limit);

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(OpenAiChatModel.builder()
                        .apiKey("demo")
                        .modelName(GPT_4_O_MINI)
                        .build())
                .build();

        return assistant.chat(request.userId(), request.question());
    }

    private String formatPrompt(String question, String context) {
        return String.format("""
            Você é um chatbot especializado em esclarecer dúvidas sobre o regimento interno e respeita as diretrizes
            dadas para um bom chatbot de um condomínio.
            Seu objetivo é fornecer informações verdadeiras, sempre com base no texto oficial do regimento.
            Responda indicando as páginas e parágrafos que contêm a informação solicitada, sem emitir opiniões,
            interpretações ou extrapolações.

            Diretrizes:
            1. Responda apenas perguntas diretamente relacionadas ao regimento do condomínio.
            2. Caso a pergunta esteja fora do escopo do regimento, informe educadamente que não pode respondê-la,
               reforçando que seu foco é exclusivamente o regimento do condomínio.
            3. Não inclua comentários ou informações que não estejam no texto oficial.

            Exemplo de resposta adequada:
            - "A informação solicitada está no capítulo 3, página 12, parágrafo 4."
            
            Responda a pergunta com base no seguinte contexto:
            %s
            
            Question: %s
            """, context, question);
    }

    @Override
    public String getResponseExtendedWithInMemoryRag(ChatRequest request) {
        return ragAssistantInMemory.chat(request.userId(), request.question());
    }

}
