package com.dereekb.gae.model.extension.taskqueue.task.iterate;

import com.dereekb.gae.model.extension.taskqueue.deprecated.api.CustomTaskContinuationBuilder;
import com.dereekb.gae.model.extension.taskqueue.deprecated.api.CustomTaskInfo;
import com.dereekb.gae.server.taskqueue.system.TaskRequestSystem;
import com.dereekb.gae.server.taskqueue.system.impl.TaskParameterImpl;
import com.dereekb.gae.server.taskqueue.system.impl.TaskRequestImpl;

@Deprecated
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
