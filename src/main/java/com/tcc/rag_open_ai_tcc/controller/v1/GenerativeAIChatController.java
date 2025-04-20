package com.tcc.rag_open_ai_tcc.controller.v1;

import com.tcc.rag_open_ai_tcc.dto.ChatRequest;
import com.tcc.rag_open_ai_tcc.dto.ChatResponse;
import com.tcc.rag_open_ai_tcc.service.v1.GenAIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class GenerativeAIChatController {

    private final GenAIService genAIService;

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        return ResponseEntity.ok(new ChatResponse(genAIService.getResponseSimple(chatRequest)));
    }

    @PostMapping("/inMemory")
    public ResponseEntity<ChatResponse> chatWithDocumentInMemory(@RequestBody ChatRequest chatRequest) {
        log.info("Message send to in memory rag");
        return ResponseEntity.ok(new ChatResponse(genAIService.getResponseExtendedWithInMemoryRag(chatRequest)));
    }

}
