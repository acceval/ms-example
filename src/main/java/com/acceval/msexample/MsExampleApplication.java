package com.acceval.msexample;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

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

	@Bean
	public Filter someFilterRegistration() {
		CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
		loggingFilter.setIncludeClientInfo(true);
		loggingFilter.setIncludeQueryString(true);
		loggingFilter.setIncludePayload(true);
		loggingFilter.setMaxPayloadLength(9999);

		return loggingFilter;
	}
}
