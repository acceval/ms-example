package com.acceval.msexample.workflow;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;

/**
 * TODO: Document this
 *
 * @author Julian
 */
@Component
public class WorkflowInitializer {
	private final RestTemplate restTemplate;

	public WorkflowInitializer(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@PostConstruct
	public void registerWorkflow() {
//		try {
//			HttpHeaders httpHeaders = new HttpHeaders();
//			httpHeaders.setContentType(MediaType.APPLICATION_XML);
//			HttpEntity<ClassPathResource> httpEntity =
//					new HttpEntity<>(new ClassPathResource("workflow/ConditionRecordWFL.bpmn"), httpHeaders);
//
//			URI uri = restTemplate.postForLocation("http://localhost:8092/register/ConditionRecordWFL.bpmn", httpEntity) ;
//
//			System.out.println("======================================");
//			System.out.println(uri);
//			System.out.println("======================================");
//		} catch (HttpClientErrorException ex) {
//			System.out.println("======================================");
//			System.out.println(ex.getResponseBodyAsString());
//			System.out.println("======================================");
//		}
	}
}
