package com.tcc.rag_open_ai_tcc.service;

import com.tcc.rag_open_ai_tcc.dto.ChatRequest;

public interface GenAIService {

    String getResponse(ChatRequest request);

    String getResponseSimple(ChatRequest request);

    String getResponse(String id, String message);

}
