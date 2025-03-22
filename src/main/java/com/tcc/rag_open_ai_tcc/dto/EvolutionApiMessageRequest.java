package com.tcc.rag_open_ai_tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EvolutionApiMessageRequest {
    private String number;
    private String text;
}
