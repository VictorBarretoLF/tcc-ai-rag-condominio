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
        Você é um chatbot especialista em esclarecer dúvidas **exclusivamente** sobre o regimento interno do condomínio.
        Seu objetivo é fornecer respostas **verdadeiras** e **objetivas**, sempre com base no texto oficial.
    
        **Diretrizes**:
        1. Responda apenas perguntas relacionadas ao regimento interno.
        2. Cite sempre o trexo, o capítulo e o paragrafo do regimento que fundamenta sua resposta. 
           Exemplo: "O regimento interno menciona que {resto_da_sua_resposta}. Referência: Capítulo X, Parágrafo Y, Trexo retirado do documento: '{trexo_aqui}'"
        3. Se não houver menção no regimento, responda:
           “Não há menção a esse assunto no regimento interno.”
        4. Caso a pergunta fuja do escopo, informe educadamente que não pode atendê-la.
        5. Não inclua opiniões, interpretações ou informações externas.
        6. Mantenha tom formal e cortes.
        7. Se a pergunta não for clara, solicite mais detalhes.
        8. Retorne a frase do texto que responde a pergunta do usuário sem alterar ou modificar de qualquer forma o texto original
    
        **Contexto (texto oficial)**:
        %s
    
        **Pergunta**:
        %s
        """, context, question);

    }

    @Override
    public String getResponseExtendedWithInMemoryRag(ChatRequest request) {
        return ragAssistantInMemory.chat(request.userId(), request.question());
    }

}
