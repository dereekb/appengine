package com.dereekb.gae.server.taskqueue.builder;

import java.util.ArrayList;
import java.util.Collection;

import com.dereekb.gae.server.taskqueue.system.TaskParameter;
import com.dereekb.gae.server.taskqueue.system.TaskRequest;
import com.dereekb.gae.server.taskqueue.system.TaskRequestImpl;
import com.dereekb.gae.server.taskqueue.system.TaskRequestTiming;
import com.dereekb.gae.server.taskqueue.system.TaskRequestTimingImpl;
import com.dereekb.gae.server.taskqueue.system.TaskRequestTimingType;


/**
 * Default implementation of {@link TaskRequestCopier} for copying
 * {@link TaskRequest} instances with a new {@link TaskRequestImpl} .
 *
 * @author dereekb
 *
 */
public class TaskRequestCopierImpl
        implements TaskRequestCopier<TaskRequestImpl> {

	@Override
	public TaskRequestImpl fullyCopyRequest(TaskRequest request) {
		TaskRequestImpl copy = this.partialCopyRequest(request);

		// Copy Headers
		Collection<TaskParameter> headers = request.getHeaders();
		if (headers != null) {
			headers = new ArrayList<TaskParameter>(headers);
			copy.setHeaders(headers);
		}

		// Copy Parameters
		Collection<TaskParameter> parameters = request.getParameters();
		if (parameters != null) {
			parameters = new ArrayList<TaskParameter>(parameters);
			copy.setParameters(parameters);
		}

		return copy;
	}

	@Override
	public TaskRequestImpl partialCopyRequest(TaskRequest request) {
		TaskRequestImpl copy = new TaskRequestImpl();

		copy.setUrl(request.getUrl());
		copy.setMethod(request.getMethod());

		TaskRequestTiming timing = copy.getTimings();
		if (timing != null) {
			TaskRequestTimingImpl timingCopy = this.copyTiming(timing);
			copy.setTimings(timingCopy);
		}

		return copy;
	}

	public TaskRequestTimingImpl copyTiming(TaskRequestTiming timing) {
		TaskRequestTimingType type = timing.getTimingType();
		Long time = timing.getTime();
		TaskRequestTimingImpl copy = new TaskRequestTimingImpl(type, time);
		return copy;
	}

}
