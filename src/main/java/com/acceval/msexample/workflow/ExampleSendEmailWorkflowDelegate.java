package com.acceval.msexample.workflow;

import com.acceval.workflow.client.WorkflowListenerDelegate;
import com.acceval.workflow.client.WorkflowListenerDelegateParams;
import com.acceval.workflow.client.signal.SignalCommands;

public class ExampleSendEmailWorkflowDelegate implements WorkflowListenerDelegate {
	@Override
	public void execute(SignalCommands commands, WorkflowListenerDelegateParams params) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
