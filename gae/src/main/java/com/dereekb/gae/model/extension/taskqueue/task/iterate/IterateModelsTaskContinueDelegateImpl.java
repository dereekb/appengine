package com.dereekb.gae.model.extension.taskqueue.task.iterate;

import com.dereekb.gae.model.extension.taskqueue.api.CustomTaskContinuationBuilder;
import com.dereekb.gae.model.extension.taskqueue.api.CustomTaskInfo;
import com.dereekb.gae.server.taskqueue.system.TaskParameterImpl;
import com.dereekb.gae.server.taskqueue.system.TaskRequestImpl;
import com.dereekb.gae.server.taskqueue.system.TaskRequestSystem;

public class IterateModelsTaskContinueDelegateImpl
        implements IterateModelsTaskContinueDelegate {

	private String cursorParam = IterateModelsTask.CURSOR_PARAM;

	private CustomTaskContinuationBuilder builder;
	private TaskRequestSystem system;

	@Override
	public void continueIteration(CustomTaskInfo request,
	                              String cursorString) {

		TaskRequestImpl taskRequest = this.builder.buildRequest(request);

		// Replace TaskParameter
		TaskParameterImpl cursorParameter = new TaskParameterImpl(this.cursorParam, cursorString);
		taskRequest.replaceParameter(cursorParameter);

		this.system.submitRequest(taskRequest);
	}

}
