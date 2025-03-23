package com.tcc.rag_open_ai_tcc.service.v1;

public interface EmbeddingComponent {

    public void loadSingleDocument();
    public void saveEmbeddingOnPostgresql();

}
