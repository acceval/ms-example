package com.acceval.msexample.workflow;

import com.acceval.msexample.repository.TypeRepository;
import com.acceval.workflow.client.SignalableWorkflowListenerDelegate;
import com.acceval.workflow.client.WorkflowListenerDelegateParams;
import com.acceval.workflow.client.signal.SignalCommands;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

public class ExampleInitializeVariableSignalableWorkflowDelegate implements SignalableWorkflowListenerDelegate {
	@Autowired
	private TypeRepository typeRepository;

	@Override
	public void execute(SignalCommands commands, WorkflowListenerDelegateParams params) {
		Map<String, Object> vars = new HashMap<>();

		StreamSupport.stream(typeRepository.findAll().spliterator(), false)
				.forEach(v -> vars.put(v.getValue(), v.getLabel()));

		commands.setVariables(vars);
	}
}
