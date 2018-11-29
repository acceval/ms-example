package com.acceval.msexample.workflow;

import com.acceval.workflow.client.WorkflowListenerDelegate;
import com.acceval.workflow.client.WorkflowListenerDelegateParams;

public class ExampleWorkflowService implements WorkflowListenerDelegate {
	@Override
	public void execute(WorkflowListenerDelegateParams params) {
		// save to db?
		// etc
		System.out.println(params);
	}
}
