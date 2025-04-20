package com.tcc.rag_open_ai_tcc.service.v1.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import com.tcc.rag_open_ai_tcc.model.DocumentEmbedding;
import com.tcc.rag_open_ai_tcc.repository.DocumentEmbeddingRepository;
import com.tcc.rag_open_ai_tcc.service.v1.EmbeddingComponent;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;
import static dev.langchain4j.data.document.splitter.DocumentSplitters.recursive;

@Component
@RequiredArgsConstructor
public class EmbeddingComponentImpl implements EmbeddingComponent {

    private final OpenAiEmbeddingModel openAiEmbeddingModel;
    private final DocumentEmbeddingRepository documentEmbeddingRepository;

    public UUID saveEmbeddingPDF() {
        final var fileName = "test.pdf";
        final var filePath = resolveFilePath(fileName);
        final var uuid = java.util.UUID.randomUUID();

        final var document = loadDocument(filePath, new ApachePdfBoxDocumentParser());

        recursive(300, 10).split(document)
                .forEach(segment -> {
                    processSegment(segment, uuid);
                });

        return uuid;
    }

    public UUID saveEmbeddingTxt() {
        final var fileName = "test.txt";
        final var filePath = resolveFilePath(fileName);
        final var uuid = java.util.UUID.randomUUID();

        final var document = loadDocument(filePath, new TextDocumentParser());

        recursive(300, 10).split(document)
                .forEach(segment -> {
                    processSegment(segment, uuid);
                });

        return uuid;
    }

    private Path resolveFilePath(String fileName) {
        String currentDir = System.getProperty("user.dir");
        return Paths.get(currentDir, fileName);
    }

    private void processSegment(TextSegment segment, UUID documentId) {
        float[] vector = openAiEmbeddingModel.embed(segment.text()).content().vector();

        DocumentEmbedding embedding = new DocumentEmbedding();
        embedding.setContent(segment.text());
        embedding.setEmbedding(vector);
        embedding.setFilename(documentId.toString());
        embedding.setFileId(documentId);

        documentEmbeddingRepository.save(embedding);
    }

}
