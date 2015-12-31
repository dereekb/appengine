package com.dereekb.gae.server.taskqueue.builder.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.taskqueue.builder.TaskRequestCopier;
import com.dereekb.gae.server.taskqueue.builder.TaskRequestSplitter;
import com.dereekb.gae.server.taskqueue.system.TaskParameter;
import com.dereekb.gae.server.taskqueue.system.TaskRequest;
import com.dereekb.gae.server.taskqueue.system.filter.TaskParameterFilter;
import com.dereekb.gae.server.taskqueue.system.impl.TaskParameterImpl;
import com.dereekb.gae.server.taskqueue.system.impl.TaskRequestImpl;
import com.dereekb.gae.utilities.filters.FilterResults;

/**
 * Utility class for splitting up requests at a parameter.
 *
 * @author dereekb
 *
 */
public class TaskRequestSplitterImpl
        implements TaskRequestSplitter<TaskRequestImpl> {

	public static final String DEFAULT_SPLIT_REGEX = ",";

	private String splitter = TaskRequestSplitterImpl.DEFAULT_SPLIT_REGEX;
	private TaskRequestCopier<TaskRequestImpl> taskCopier = new TaskRequestCopierImpl();

	public String getSplitter() {
		return this.splitter;
	}

	public void setSplitter(String splitter) {
		this.splitter = splitter;
	}

	public TaskRequestCopier<TaskRequestImpl> getTaskCopier() {
		return this.taskCopier;
	}

	public void setTaskCopier(TaskRequestCopier<TaskRequestImpl> taskCopier) {
		this.taskCopier = taskCopier;
	}

	@Override
	public List<TaskRequestImpl> splitRequestAtParameter(String parameter,
	                                                     TaskRequest request) {

		TaskParameterFilter filter = new TaskParameterFilter();
		filter.setParameter(parameter);

		Collection<TaskParameter> parameters = request.getParameters();
		FilterResults<TaskParameter> filtered = filter.filterObjects(parameters);

		// Retain parameters that aren't being split.
		List<TaskParameter> passing = filtered.getPassingObjects();
		List<TaskParameter> otherParameters = filtered.getFailingObjects();

		TaskParameter splitParameter = null;

		if (passing.size() == 0) {
			throw new IllegalArgumentException("Passed parameter '" + parameter + "' does not exist.");
		} else {
			splitParameter = passing.get(0);
		}

		// Split the parameter value.
		String parameterValue = splitParameter.getValue();
		String[] splitValues = parameterValue.split(this.splitter);

		// Main copy target.
		TaskRequestImpl copyTarget = this.taskCopier.partialCopyRequest(request);
		copyTarget.setHeaders(request.getHeaders());
		copyTarget.setParameters(otherParameters);

		List<TaskRequestImpl> splitRequests = new ArrayList<TaskRequestImpl>();

		for (String splitValue : splitValues) {
			TaskRequestImpl splitRequest = this.taskCopier.fullyCopyRequest(copyTarget);

			// Set parameters
			TaskParameter newParameter = new TaskParameterImpl(parameter, splitValue);
			Collection<TaskParameter> splitParameters = splitRequest.getParameters();
			splitParameters.add(newParameter);

			splitRequests.add(splitRequest);
		}

		return splitRequests;
	}

}
