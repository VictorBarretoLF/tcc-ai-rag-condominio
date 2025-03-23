package com.tcc.rag_open_ai_tcc.service.impl;

import com.tcc.rag_open_ai_tcc.dto.ChatRequest;
import com.tcc.rag_open_ai_tcc.service.Assistant;
import com.tcc.rag_open_ai_tcc.service.GenAIService;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

@Service
@RequiredArgsConstructor
public class GenAIServiceImpl implements GenAIService {

    private final Assistant assistant;

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

}
