package com.tcc.rag_open_ai_tcc.service.v1;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface RAGAssistantInMemory {

    @SystemMessage(
        """
            You are a helpful assistant. Try to respond in a fair and warm manner.
            If you don't know answer, just tell it.
        """
    )
    String chat(@MemoryId String memoryId, @UserMessage String userMessage);
}
