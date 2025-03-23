package com.tcc.rag_open_ai_tcc.service.impl;

import com.tcc.rag_open_ai_tcc.service.EmbeddingComponent;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@Component
@RequiredArgsConstructor
public class EmbeddingComponentImpl implements EmbeddingComponent {

    private final OpenAiEmbeddingModel openAiEmbeddingModel;
    private final EmbeddingStore embeddingStore;

    @Override
    public void loadSingleDocument() {
        String currentDir = System.getProperty("user.dir");
        String fileName = "test.pdf";

        Document document = loadDocument(currentDir + currentDir, new ApachePdfBoxDocumentParser());

        final var embeddingStoreIngestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(300, 10))
                .embeddingModel(openAiEmbeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        embeddingStoreIngestor.ingest(document);
    }

}
