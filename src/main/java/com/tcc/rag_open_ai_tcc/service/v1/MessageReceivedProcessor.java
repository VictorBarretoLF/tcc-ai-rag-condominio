package com.tcc.rag_open_ai_tcc.service.v1;

import com.tcc.rag_open_ai_tcc.dto.EvolutionApiPayload;

public interface MessageReceivedProcessor {
    void processIncomingMessage(EvolutionApiPayload payload);
}
