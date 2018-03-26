package com.acceval.msexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.acceval.core.jackson.module.APIJavaTimeModule;
import com.fasterxml.jackson.databind.Module;

@SpringBootApplication
public class MsExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsExampleApplication.class, args);
	}

	@Bean
	public Module provideModule() {
		return new APIJavaTimeModule();
	}
}
