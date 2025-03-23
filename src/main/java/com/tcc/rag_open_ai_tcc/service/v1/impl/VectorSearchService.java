package com.tcc.rag_open_ai_tcc.service.v1.impl;

import com.tcc.rag_open_ai_tcc.model.DocumentEmbedding;
import com.tcc.rag_open_ai_tcc.repository.DocumentEmbeddingRepository;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VectorSearchService {

    private final EmbeddingModel embeddingModel;
    private final DocumentEmbeddingRepository repository;

    public String findRelevantContext(String question) {
        // Generate question embedding
        Embedding questionEmbedding = embeddingModel.embed(question).content();

        // Search for top 3 most relevant document chunks
        List<DocumentEmbedding> results = repository.findTop3ByEmbeddingOrderByCosineSimilarity(questionEmbedding.vector());

        if (results.isEmpty()) {
            return "No relevant context found.";
        }

        // Combine the relevant text chunks
        StringBuilder context = new StringBuilder();
        results.forEach(doc -> context.append(doc.getContent()).append("\n\n"));

        return context.toString();
    }

}
