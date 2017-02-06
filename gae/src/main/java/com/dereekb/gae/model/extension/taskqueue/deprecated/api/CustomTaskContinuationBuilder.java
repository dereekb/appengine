package com.dereekb.gae.model.extension.taskqueue.deprecated.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.server.taskqueue.builder.TaskRequestBuilder;
import com.dereekb.gae.server.taskqueue.system.KeyedEncodedParameter;
import com.dereekb.gae.server.taskqueue.system.TaskRequest;
import com.dereekb.gae.server.taskqueue.system.impl.KeyedEncodedParameterImpl;
import com.dereekb.gae.server.taskqueue.system.impl.TaskRequestImpl;

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

		// todo: Set anything else?

		// Set Continuation Url
		taskRequest.setUrl(this.continueUrl);

		// Build headers
		Integer step = input.getTaskStep();
		step += 1;

		String stepString = step.toString();
		KeyedEncodedParameter stepHeader = new KeyedEncodedParameterImpl(this.stepHeader, stepString);

		List<KeyedEncodedParameter> headers = new ArrayList<KeyedEncodedParameter>();
		headers.add(stepHeader);

		taskRequest.setParameters(headers);

		// Copy Parameters
		Map<String, String> map = input.getKeyedEncodedParameters();

		Collection<KeyedEncodedParameterImpl> KeyedEncodedParameters = KeyedEncodedParameterImpl.makeParametersWithMap(map);

		List<KeyedEncodedParameter> parameters = new ArrayList<KeyedEncodedParameter>();
		parameters.addAll(KeyedEncodedParameters);

		taskRequest.setParameters(parameters);

		return taskRequest;
	}

}
