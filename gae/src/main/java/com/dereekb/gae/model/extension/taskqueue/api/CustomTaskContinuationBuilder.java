package com.dereekb.gae.model.extension.taskqueue.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.server.taskqueue.builder.TaskRequestBuilder;
import com.dereekb.gae.server.taskqueue.system.TaskParameter;
import com.dereekb.gae.server.taskqueue.system.TaskParameterImpl;
import com.dereekb.gae.server.taskqueue.system.TaskRequest;
import com.dereekb.gae.server.taskqueue.system.TaskRequestImpl;

/**
 * Utility used to create continuations of {@link CustomTaskInfo} tasks.
 *
 * @author dereekb
 */
@Deprecated
public class CustomTaskContinuationBuilder
        implements TaskRequestBuilder<CustomTaskInfo> {

	private String stepHeader = CustomTaskInfo.DEFAULT_TASK_STEP_HEADER;
	private String continueUrl = "";

	public CustomTaskContinuationBuilder(String continueUrl) {
		this.continueUrl = continueUrl;
	}

	public String getStepHeader() {
		return this.stepHeader;
	}

	public void setStepHeader(String stepHeader) {
		this.stepHeader = stepHeader;
	}

	public String getContinueUrl() {
		return this.continueUrl;
	}

	public void setContinueUrl(String continueUrl) {
		this.continueUrl = continueUrl;
	}

	@Override
	public List<TaskRequest> buildRequests(Iterable<CustomTaskInfo> input) {
		List<TaskRequest> requests = new ArrayList<TaskRequest>();

		for (CustomTaskInfo info : input) {
			TaskRequest request = this.buildRequest(info);
			requests.add(request);
		}

		return requests;
	}

	public TaskRequestImpl buildRequest(CustomTaskInfo input) {
		TaskRequestImpl taskRequest = new TaskRequestImpl();

		// TODO: Set anything else?

		// Set Continuation Url
		taskRequest.setUrl(this.continueUrl);

		// Build headers
		Integer step = input.getTaskStep();
		step += 1;

		String stepString = step.toString();
		TaskParameter stepHeader = new TaskParameterImpl(this.stepHeader, stepString);

		List<TaskParameter> headers = new ArrayList<TaskParameter>();
		headers.add(stepHeader);

		taskRequest.setParameters(headers);

		// Copy Parameters
		Map<String, String> map = input.getTaskParameters();

		Collection<TaskParameterImpl> taskParameters = TaskParameterImpl.makeParametersWithMap(map);

		List<TaskParameter> parameters = new ArrayList<TaskParameter>();
		parameters.addAll(taskParameters);

		taskRequest.setParameters(parameters);

		return taskRequest;
	}

}
