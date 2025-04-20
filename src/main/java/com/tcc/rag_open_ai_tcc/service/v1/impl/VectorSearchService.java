package com.tcc.rag_open_ai_tcc.service.v1.impl;

import com.tcc.rag_open_ai_tcc.repository.DocumentEmbeddingRepository;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VectorSearchService {

    private final EmbeddingModel embeddingModel;
    private final DocumentEmbeddingRepository repository;

    public String findRelevantContext(String question, UUID fileId, int limit) {
        Embedding questionEmbedding = embeddingModel.embed(question).content();

        var results = repository.findTopSimilarInDocument(fileId.toString(), questionEmbedding.vector(), limit);

        if (results.isEmpty()) {
            return "No relevant context found.";
        }

        return results.stream()
            .map(row -> (String) row[2])
            .collect(Collectors.joining("\n\n"));
    }

}
