package com.acceval.msexample.workflow;

import com.acceval.workflow.client.Workflow;
import com.acceval.workflow.client.WorkflowRegistrar;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RequestMapping(path = "/workflow")
@RestController
public class ExampleWorkflowController {
	private final Workflow workflow1;
	private final Workflow workflow2;
	private final Workflow workflow3;
	private final WorkflowRegistrar registrar;

	public ExampleWorkflowController(
			@Qualifier(WorkflowQualifiers.EXAMPLE_WORKFLOW_1) Workflow workflow1,
			@Qualifier(WorkflowQualifiers.EXAMPLE_WORKFLOW_2) Workflow workflow2,
			@Qualifier(WorkflowQualifiers.EXAMPLE_WORKFLOW_3) Workflow workflow3,
			WorkflowRegistrar registrar) {
		this.workflow1 = workflow1;
		this.workflow2 = workflow2;
		this.workflow3 = workflow3;
		this.registrar = registrar;
	}

	@PostMapping(path = "/start1")
	public void startWorkflow1(@RequestHeader("user") String user, @RequestBody ExampleWorkflowData data) {
		workflow1.start(data);
	}

	@PostMapping(path = "/start2")
	public void startWorkflow2(@RequestHeader("user") String user, @RequestBody ExampleWorkflowData data) {
		workflow2.start(data);
	}

	@PostMapping(path = "/start3")
	public void startWorkflow3(@RequestHeader("user") String user, @RequestBody ExampleWorkflowData data) {
		workflow3.start(data);
	}

	@GetMapping(path = "/register")
	public void registerWorkflow() throws IOException {
		registrar.registerWorkflow(workflow1);
	}

	@GetMapping(path = "/taskList")
	public List<Object> taskList() {
		// TODO: retrieve tasks list from server

		return Collections.emptyList();
	}
}
