package com.tcc.rag_open_ai_tcc.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "document_embeddings")
@Data
public class DocumentEmbedding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String content;

    @Column(nullable = false, columnDefinition = "vector(1536)")
    private float[] embedding;

    private String filename;

}
