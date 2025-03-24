package com.tcc.rag_open_ai_tcc.service.v1.impl;

import java.nio.file.Paths;
import java.util.List;

import com.tcc.rag_open_ai_tcc.model.DocumentEmbedding;
import com.tcc.rag_open_ai_tcc.repository.DocumentEmbeddingRepository;
import com.tcc.rag_open_ai_tcc.service.v1.EmbeddingComponent;
import dev.langchain4j.data.document.Document;
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

    public void saveEmbeddingOnPostgresql() {
        String currentDir = System.getProperty("user.dir");
        String fileName = "test.pdf";
        String filePath = Paths.get(currentDir, fileName).toString();

        Document document = loadDocument(filePath, new ApachePdfBoxDocumentParser());

        List<TextSegment> segments = recursive(300, 10).split(document);

        segments.forEach(segment -> {
            Embedding embedding = openAiEmbeddingModel.embed(segment.text()).content();

            DocumentEmbedding documentEmbedding = new DocumentEmbedding();
            documentEmbedding.setContent(segment.text());
            documentEmbedding.setEmbedding(embedding.vector());
            documentEmbedding.setFilename("test.pdf");

            documentEmbeddingRepository.save(documentEmbedding);
        });
    }

}
