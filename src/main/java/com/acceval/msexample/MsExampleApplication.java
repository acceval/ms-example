package com.acceval.msexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import com.acceval.core.audit.IgnoreAuditScan;
import com.acceval.core.jackson.module.APIJavaTimeModule;
import com.acceval.core.service.FileStorageService;
import com.acceval.msexample.security.CustomUserInfoTokenServices;
import com.acceval.workflow.client.WorkflowClient;

import feign.RequestInterceptor;

@SpringBootApplication
@EnableResourceServer
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableDiscoveryClient
@EnableConfigurationProperties
@EnableFeignClients
@SpringBootConfiguration
@Configuration
@ComponentScan(basePackages = { "com.acceval" }, excludeFilters = {@Filter(IgnoreAuditScan.class), 
		@Filter(type=FilterType.REGEX, pattern="com\\.acceval\\.workflow\\..*")})
public class MsExampleApplication implements ResourceServerConfigurer {

	@Autowired
	private ResourceServerProperties sso;
	
	public final static String LISTENER_QUEUE_NAME = "example-queue";
	public final static String EXCHANGE_NAME = "smarttradz.topic";
	
	
	public static void main(String[] args) {

		SpringApplication.run(MsExampleApplication.class, args);
	}

    @Bean
    CommandLineRunner init(FileStorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
    
	@Bean
	Jackson2ObjectMapperBuilderCustomizer localDateConverter() {
		return builder -> builder.modulesToInstall(new APIJavaTimeModule());
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

	}

	@Bean
	@ConfigurationProperties(prefix = "security.oauth2.client")
	public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
		return new ClientCredentialsResourceDetails();
	}

	@Bean
	public RequestInterceptor oauth2FeignRequestInterceptor() {
		return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
	}


	@Bean
	public OAuth2RestTemplate clientCredentialsRestTemplate() {
		return new OAuth2RestTemplate(clientCredentialsResourceDetails());
	}

	@Bean
	public ResourceServerTokenServices tokenServices() {
		return new CustomUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http		
			.authorizeRequests()
				.antMatchers("/").permitAll()				
				.anyRequest().authenticated()
			.and()
				.cors()
			.and()
				.csrf().disable();
	}
	
}
