package com.tcc.rag_open_ai_tcc.controller.v2;

import com.tcc.rag_open_ai_tcc.dto.ChatRequest;
import com.tcc.rag_open_ai_tcc.dto.ChatResponse;
import com.tcc.rag_open_ai_tcc.service.v1.GenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/chat")
public class GenerativeControllerV2 {

    private final GenAIService genAIService;

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        return ResponseEntity.ok(new ChatResponse(genAIService.getResponse(chatRequest)));
    }

    @PostMapping("/rag")
    public ResponseEntity<ChatResponse> chatWithContext(@RequestBody ChatRequest chatRequest) {
        return ResponseEntity.ok(new ChatResponse(genAIService.getRagContextResponse(chatRequest)));
    }

}
