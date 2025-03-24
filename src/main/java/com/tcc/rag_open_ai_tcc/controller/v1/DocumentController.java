package com.tcc.rag_open_ai_tcc.controller.v1;

import com.tcc.rag_open_ai_tcc.dto.ChatRequest;
import com.tcc.rag_open_ai_tcc.dto.ChatResponse;
import com.tcc.rag_open_ai_tcc.service.v1.EmbeddingComponent;
import com.tcc.rag_open_ai_tcc.service.v1.GenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private final EmbeddingComponent embeddingComponent;
    private final GenAIService genAIService;

    @PostMapping("/loadDocumentAndSaveOnPostgresql")
    public ResponseEntity<String> loadDocumentAndSaveOnPostgresql() {
        try {
            embeddingComponent.saveEmbeddingOnPostgresql();
            return ResponseEntity.ok("Document processed and saved to database successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing document: " + e.getMessage());
        }
    }

}