package com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestCopier;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSplitter;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.filter.KeyedEncodedParameterFilter;
import com.dereekb.gae.utilities.misc.parameters.impl.KeyedEncodedParameterImpl;

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

		KeyedEncodedParameterFilter filter = new KeyedEncodedParameterFilter();
		filter.setParameter(parameter);

		Collection<KeyedEncodedParameter> parameters = request.getParameters();
		FilterResults<KeyedEncodedParameter> filtered = filter.filterObjects(parameters);

		// Retain parameters that aren't being split.
		List<KeyedEncodedParameter> passing = filtered.getPassingObjects();
		List<KeyedEncodedParameter> otherParameters = filtered.getFailingObjects();

		KeyedEncodedParameter splitParameter = null;

		if (passing.size() == 0) {
			throw new IllegalArgumentException("Passed parameter '" + parameter + "' does not exist.");
		} else {
			splitParameter = passing.get(0);
		}

		// Split the parameter value.
		String parameterValue = splitParameter.getParameterString();
		String[] splitValues = parameterValue.split(this.splitter);

		// Main copy target.
		TaskRequestImpl copyTarget = this.taskCopier.partialCopyRequest(request);
		copyTarget.setHeaders(request.getHeaders());
		copyTarget.setParameters(otherParameters);

		List<TaskRequestImpl> splitRequests = new ArrayList<TaskRequestImpl>();

		for (String splitValue : splitValues) {
			TaskRequestImpl splitRequest = this.taskCopier.fullyCopyRequest(copyTarget);

			// Set parameters
			KeyedEncodedParameter newParameter = new KeyedEncodedParameterImpl(parameter, splitValue);
			Collection<KeyedEncodedParameter> splitParameters = splitRequest.getParameters();
			splitParameters.add(newParameter);

			splitRequests.add(splitRequest);
		}

		return splitRequests;
	}

}
