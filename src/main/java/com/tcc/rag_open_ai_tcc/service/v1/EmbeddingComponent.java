package com.tcc.rag_open_ai_tcc.service.v1;

import java.util.UUID;

public interface EmbeddingComponent {

    public UUID saveEmbeddingPDF();
    public UUID saveEmbeddingTxt();

}
