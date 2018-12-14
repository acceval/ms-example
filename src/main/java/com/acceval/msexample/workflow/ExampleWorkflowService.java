package com.acceval.msexample.workflow;

import com.acceval.msexample.type.TypeRepository;
import com.acceval.workflow.client.WorkflowListenerDelegate;
import com.acceval.workflow.client.WorkflowListenerDelegateParams;
import com.acceval.workflow.client.signal.SignalCommands;
import org.springframework.beans.factory.annotation.Autowired;

public class ExampleWorkflowService implements WorkflowListenerDelegate {
	@Autowired
	private TypeRepository typeRepository;

	@Override
	public void execute(SignalCommands commands, WorkflowListenerDelegateParams params) {
		System.out.println(params);
	}
}