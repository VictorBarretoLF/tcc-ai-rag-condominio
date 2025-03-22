package com.tcc.rag_open_ai_tcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RagOpenAiTccApplication {

	public static void main(String[] args) {
		SpringApplication.run(RagOpenAiTccApplication.class, args);
	}

}
