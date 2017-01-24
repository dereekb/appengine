package com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl.TaskRequestResourceModifier;
import com.dereekb.gae.utilities.misc.path.SimplePath;
import com.dereekb.gae.utilities.misc.path.impl.SimplePathImpl;

/**
 * {@link ModelKeyTaskRequestBuilder} extension that will append a relative URL
 * for model requests.
 * 
 * TODO: Delete this type, as the modelResource path extension this adds/offers
 * seems to be in the wrong place. I suppose the schedulers use it, but it
 * doesn't really belong in this type.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * 
 * @deprecated Use {@link TaskRequestResourceModifier} in a
 *             {@link TaskRequestSender}
 *             instead.
 */
@Deprecated
public class TypedModelKeyTaskRequestBuilder<T extends UniqueModel> extends ModelKeyTaskRequestBuilder<T> {

	private SimplePath modelResource;

	protected TypedModelKeyTaskRequestBuilder() {}

	public TypedModelKeyTaskRequestBuilder(String modelResource, TaskRequest baseRequest) {
		this(new SimplePathImpl(modelResource), baseRequest);
	}

	public TypedModelKeyTaskRequestBuilder(SimplePath modelResource, TaskRequest baseRequest) {
		super.setBaseRequest(baseRequest);
		this.setModelResource(modelResource);
	}

	public SimplePath getModelResource() {
		return this.modelResource;
	}

	public void setModelResource(String modelResource) {
		this.setModelResource(new SimplePathImpl(modelResource));
	}

	public void setModelResource(SimplePath modelResource) {
		if (modelResource == null) {
			throw new IllegalArgumentException("Model SimplePath cannot be null.");
		}

		this.modelResource = modelResource;
	}

	// MARK: TaskRequestBuilder
	@Override
	protected TaskRequestImpl buildRequest(List<T> partition) {
		TaskRequestImpl request = super.buildRequest(partition);

		SimplePath path = request.getPath();
		SimplePath fullPath = this.modelResource.append(path);
		request.setPath(fullPath);

		return request;
	}

}
