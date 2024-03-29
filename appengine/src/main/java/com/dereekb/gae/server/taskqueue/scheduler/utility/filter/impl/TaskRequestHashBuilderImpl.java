package com.dereekb.gae.server.taskqueue.scheduler.utility.filter.impl;

import java.util.Collection;

import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.utility.filter.TaskRequestHashBuilder;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;

/**
 * Special tool used to build hash values for {@link TaskRequest} instances to
 * attempt to build the same hash given the same parameters.
 * <p>
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
		String url = request.getPath().toString();
		return ((url == null) ? 0 : url.hashCode());
	}

	public Integer getParametersHashcode(TaskRequest request) {
		int result = 1;
		Collection<KeyedEncodedParameter> parameters = request.getParameters();

		if (parameters != null) {
			for (KeyedEncodedParameter pair : parameters) {
				result = result + pair.hashCode();
			}
		}

		return result;
	}

}
