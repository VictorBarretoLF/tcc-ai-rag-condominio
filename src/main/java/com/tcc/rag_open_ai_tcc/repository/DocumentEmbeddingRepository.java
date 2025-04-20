package com.tcc.rag_open_ai_tcc.repository;

import java.util.List;
import java.util.UUID;

import com.tcc.rag_open_ai_tcc.model.DocumentEmbedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentEmbeddingRepository extends JpaRepository<DocumentEmbedding, Long> {

    @Query(value = """
        WITH filtered_documents AS (
            SELECT *
            FROM document_embeddings
            WHERE file_id = CAST(:fileId AS UUID)
        )
        SELECT
            fd.id,
            fd.file_id,
            fd.content,
            fd.embedding,
            fd.filename,
            1 - (fd.embedding <=> CAST(:embedding AS vector)) AS cosine_similarity
        FROM filtered_documents fd
        ORDER BY cosine_similarity DESC
        LIMIT :limit
    """, nativeQuery = true)
    List<Object[]> findTopSimilarInDocument(
            @Param("fileId") String fileUuid,
            @Param("embedding") float[] embedding,
            @Param("limit") int limit
    );
}