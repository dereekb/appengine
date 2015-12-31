package com.dereekb.gae.server.taskqueue.system.filter.impl;

import java.util.Collection;

import com.dereekb.gae.server.taskqueue.system.TaskParameter;
import com.dereekb.gae.server.taskqueue.system.TaskRequest;
import com.dereekb.gae.server.taskqueue.system.filter.TaskRequestHashBuilder;

/**
 * Special tool used to build hash values for {@link TaskRequest} instances to
 * attempt to build the same hash given the same parameters.
 *
 * Only parameters and url are considered.
 *
 * TODO: In the future if requests manage to differ only in headers, make sure
 * headers are also used.
 *
 * @author dereekb
 *
 */
public class TaskRequestHashBuilderImpl
        implements TaskRequestHashBuilder {

	private final int prime = 31;

	@Override
	public Integer readHashCode(TaskRequest request) {
		Integer hash = 1;

		hash = this.prime * hash + this.getParametersHashcode(request);
		hash = this.prime * hash + this.getUrlHashcode(request);

		return hash;
	}

	public Integer getUrlHashcode(TaskRequest request) {
		String url = request.getUrl();
		return ((url == null) ? 0 : url.hashCode());
	}

	public Integer getParametersHashcode(TaskRequest request) {
		int result = 1;
		Collection<TaskParameter> parameters = request.getParameters();

		if (parameters != null) {
			for (TaskParameter pair : parameters) {
				result = result + pair.hashCode();
			}
		}

		return result;
	}

}
