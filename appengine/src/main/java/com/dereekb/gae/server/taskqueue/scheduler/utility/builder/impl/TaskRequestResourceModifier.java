package com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl;

import com.dereekb.gae.server.taskqueue.scheduler.MutableTaskRequest;
import com.dereekb.gae.utilities.misc.path.SimplePath;
import com.dereekb.gae.utilities.misc.path.impl.SimplePathImpl;

/**
 * Used to modify {@link MutableTaskRequest} values by appending a
 * {@link SimplePath} to the current path.
 * 
 * @author dereekb
 *
 */
public class TaskRequestResourceModifier extends AbstractTaskRequestModifier {

	private SimplePath baseResource;

	public TaskRequestResourceModifier(String baseResource) throws IllegalArgumentException {
		this.setBaseResource(baseResource);
	}

	public TaskRequestResourceModifier(SimplePath baseResource) throws IllegalArgumentException {
		this.setBaseResource(baseResource);
	}

	public SimplePath getBaseResource() {
		return this.baseResource;
	}

	public void setBaseResource(String baseResource) {
		this.setBaseResource(new SimplePathImpl(baseResource));
	}

	public void setBaseResource(SimplePath baseResource) throws IllegalArgumentException {
		if (baseResource == null) {
			throw new IllegalArgumentException("Base Resource cannot be null.");
		}

		this.baseResource = baseResource;
	}

	// MARK: AbstractTaskRequestModifier
	@Override
	public MutableTaskRequest modifyRequest(MutableTaskRequest request) {

		SimplePath path = request.getPath();
		SimplePath fullPath = this.baseResource.append(path);
		request.setPath(fullPath);

		return request;
	}

	@Override
	public String toString() {
		return "TaskRequestResourceModifier [baseResource=" + this.baseResource + "]";
	}

}
