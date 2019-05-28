package com.acceval.msexample;

import com.acceval.core.jackson.module.APIJavaTimeModule;
import com.fasterxml.jackson.databind.Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.Filter;
	//testing
@EnableDiscoveryClient
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
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

	@Bean
	public RestTemplate provideRestTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
