package com.dereekb.gae.model.extension.taskqueue.request;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.utilities.misc.path.SimplePath;
import com.dereekb.gae.utilities.misc.path.impl.SimplePathImpl;

/**
 * {@link ModelKeyTaskRequestBuilder} extension that will append a relative URL
 * for model requests.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
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
