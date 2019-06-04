package com.acceval.msexample.workflow;

import com.acceval.workflow.client.Workflow;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
 * Workflows configuration
 *
 * @author Julian
 */
//@Configuration
public class WorkflowConfiguration {
	@Qualifier(WorkflowQualifiers.EXAMPLE_WORKFLOW_1)
	@Bean
	Workflow exampleWorkflow1() {
		return new Workflow("workflow/exampleWorkflow1");
	}

	@Qualifier(WorkflowQualifiers.EXAMPLE_WORKFLOW_2)
	@Bean
	Workflow exampleWorkflow2() {
		return new Workflow("workflow/exampleWorkflow2");
	}

	@Qualifier(WorkflowQualifiers.EXAMPLE_WORKFLOW_3)
	@Bean
	Workflow exampleWorkflow3() {
		return new Workflow("workflow/exampleWorkflow3");
	}
}
