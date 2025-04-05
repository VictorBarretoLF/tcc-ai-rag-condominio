package com.tcc.rag_open_ai_tcc.service.v1;

import com.tcc.rag_open_ai_tcc.dto.ChatRequest;

public interface GenAIService {

    String getResponse(ChatRequest request);

    String getResponseSimple(ChatRequest request);

    String getResponse(String id, String message);

    String getRagContextResponse(ChatRequest request);

    String getResponseExtendedWithInMemoryRag(ChatRequest request);

}
