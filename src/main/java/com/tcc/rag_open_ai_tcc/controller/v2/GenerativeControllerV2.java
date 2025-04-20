package com.tcc.rag_open_ai_tcc.controller.v2;

import com.tcc.rag_open_ai_tcc.dto.ChatRequest;
import com.tcc.rag_open_ai_tcc.dto.ChatResponse;
import com.tcc.rag_open_ai_tcc.service.v1.GenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/chat")
public class GenerativeControllerV2 {

    private final GenAIService genAIService;

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        return ResponseEntity.ok(new ChatResponse(genAIService.getResponse(chatRequest)));
    }

    @PostMapping("/contextual")
    public ResponseEntity<ChatResponse> contextualChat(
            @RequestParam UUID fileUuid,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestBody ChatRequest chatRequest) {
        return ResponseEntity.ok(
                new ChatResponse(genAIService.getRagContextResponse(chatRequest, fileUuid, limit))
        );
    }

}
