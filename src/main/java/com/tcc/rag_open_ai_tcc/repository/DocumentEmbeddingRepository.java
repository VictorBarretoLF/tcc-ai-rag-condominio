package com.tcc.rag_open_ai_tcc.repository;

import com.tcc.rag_open_ai_tcc.model.DocumentEmbedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentEmbeddingRepository extends JpaRepository<DocumentEmbedding, Long> {
}