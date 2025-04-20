package com.tcc.rag_open_ai_tcc.service.v1;

import com.tcc.rag_open_ai_tcc.dto.ChatRequest;

import java.util.UUID;

public interface GenAIService {

    String getResponse(ChatRequest request);

    String getResponseSimple(ChatRequest request);

    String getResponse(String id, String message);

    String getRagContextResponse(ChatRequest request, UUID fileId, int limit);

    String getResponseExtendedWithInMemoryRag(ChatRequest request);

}
