package com.tcc.rag_open_ai_tcc.repository;

import com.tcc.rag_open_ai_tcc.model.DocumentEmbedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentEmbeddingRepository extends JpaRepository<DocumentEmbedding, Long> {

    @Query(value = """
        SELECT *, 1 - (embedding <=> CAST(:embedding AS vector)) AS cosine_similarity
        FROM document_embeddings
        ORDER BY cosine_similarity DESC
        LIMIT 3
        """, nativeQuery = true)
    List<Object[]> findTop3ByEmbeddingOrderByCosineSimilarity(@Param("embedding") float[] embedding);

}