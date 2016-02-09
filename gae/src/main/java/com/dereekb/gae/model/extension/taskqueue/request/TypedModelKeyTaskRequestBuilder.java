package com.dereekb.gae.model.extension.taskqueue.request;

import java.net.URI;
import java.net.URISyntaxException;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskParameterImpl;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;

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

	private URI baseModelUri;

	protected TypedModelKeyTaskRequestBuilder() {}

	public TypedModelKeyTaskRequestBuilder(String stringModelUri, TaskRequest baseRequest) throws URISyntaxException {
		super.setBaseRequest(baseRequest);
		this.setBaseModelUri(stringModelUri);
	}

	public TypedModelKeyTaskRequestBuilder(URI baseModelUri, TaskRequest baseRequest) {
		super.setBaseRequest(baseRequest);
		this.setBaseModelUri(baseModelUri);
	}

	public URI getBaseModelUri() {
		return this.baseModelUri;
	}

	public void setBaseModelUri(String stringModelUri) throws URISyntaxException {
		URI uri = new URI(stringModelUri + '/');
		this.setBaseModelUri(uri);
	}

	public void setBaseModelUri(URI baseModelUri) {
		if (baseModelUri == null) {
			throw new IllegalArgumentException("Model URI cannot be null.");
		}

		this.baseModelUri = baseModelUri;
	}

	// MARK: TaskRequestBuilder
	@Override
	protected TaskRequestImpl createNewModelKeyRequest(TaskParameterImpl keyParameter) {
		TaskRequestImpl request = super.createNewModelKeyRequest(keyParameter);

		URI requestUri = request.getUri();
		URI fullUri = this.baseModelUri.resolve(requestUri);
		request.setUri(fullUri);

		return request;
	}

}
