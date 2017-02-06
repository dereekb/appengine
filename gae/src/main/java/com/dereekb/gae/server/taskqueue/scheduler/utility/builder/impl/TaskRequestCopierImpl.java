package com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequestTiming;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequestTimingType;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestTimingImpl;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestCopier;


/**
 * Default implementation of {@link TaskRequestCopier} for copying
 * {@link TaskRequest} instances with a new {@link TaskRequestImpl} .
 *
 * @author dereekb
 *
 */
public class TaskRequestCopierImpl
        implements TaskRequestCopier<TaskRequestImpl> {

	public static final TaskRequestCopierImpl SINGLETON = new TaskRequestCopierImpl();

	@Override
	public TaskRequestImpl fullyCopyRequest(TaskRequest request) {
		TaskRequestImpl copy = this.partialCopyRequest(request);

		// Copy Headers
		Collection<KeyedEncodedParameter> headers = request.getHeaders();
		if (headers != null) {
			headers = new ArrayList<KeyedEncodedParameter>(headers);
			copy.setHeaders(headers);
		}

		// Copy Parameters
		Collection<KeyedEncodedParameter> parameters = request.getParameters();
		if (parameters != null) {
			parameters = new ArrayList<KeyedEncodedParameter>(parameters);
			copy.setParameters(parameters);
		}

		return copy;
	}

	@Override
	public TaskRequestImpl partialCopyRequest(TaskRequest request) {
		TaskRequestImpl copy = new TaskRequestImpl();

		copy.setPath(request.getPath());
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
