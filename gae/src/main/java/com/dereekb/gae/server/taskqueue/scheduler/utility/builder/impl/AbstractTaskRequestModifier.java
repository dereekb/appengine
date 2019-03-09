package com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.taskqueue.scheduler.MutableTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestModifier;

/**
 * Abstract {@link TaskRequestModifier} implementation.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractTaskRequestModifier
        implements TaskRequestModifier {

	// MARK: TaskRequestModifier
	@Override
	public Collection<MutableTaskRequest> modifyRequests(Collection<? extends MutableTaskRequest> requests) {
		List<MutableTaskRequest> modifiedRequests = new ArrayList<MutableTaskRequest>();

		for (MutableTaskRequest request : requests) {
			MutableTaskRequest result = this.modifyRequest(request);
			modifiedRequests.add(result);
		}

		return modifiedRequests;
	}

	@Override
	public abstract MutableTaskRequest modifyRequest(MutableTaskRequest request);

}
