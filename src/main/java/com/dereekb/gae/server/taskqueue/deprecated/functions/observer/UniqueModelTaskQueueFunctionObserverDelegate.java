package com.dereekb.gae.server.taskqueue.deprecated.functions.observer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.deprecated.TaskQueueParamPair;
import com.dereekb.gae.server.taskqueue.deprecated.TaskQueuePushRequest;
import com.dereekb.gae.utilities.collections.batch.BatchGenerator;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Delegate for a {@link ModelsTaskQueueFunctionObserver} for building requests
 * from the input models.
 *
 * @author dereekb
 *
 * @param <T>
 */
@Deprecated
public class UniqueModelTaskQueueFunctionObserverDelegate<T extends UniqueModel>
        implements TaskQueueObjectRequestBuilder<T> {

	private List<TaskQueueParamPair> requestParams;
	private Integer maxKeysPerRequest = 25;
	private String keysParamName = "keys";
	private String requestUri;
	private Method method;

	public UniqueModelTaskQueueFunctionObserverDelegate(String requestUri) {
		super();
		this.requestUri = requestUri;
	}

	@Override
	public Collection<TaskQueuePushRequest> buildModelRequests(Iterable<T> objects) {

		List<TaskQueuePushRequest> requests = new ArrayList<TaskQueuePushRequest>();
		List<ModelKey> keys = ModelKey.readModelKeys(objects);
		List<List<ModelKey>> keyBatches = BatchGenerator.createBatches(keys, this.maxKeysPerRequest);

		for (List<ModelKey> batch : keyBatches) {
			TaskQueuePushRequest request = this.makeRequest(batch);
			requests.add(request);
		}

		return requests;
	}

	private TaskQueuePushRequest makeRequest(List<ModelKey> batch) {
		TaskQueuePushRequest request = new TaskQueuePushRequest(this.requestUri);

		Collection<TaskQueueParamPair> params = this.makeRequestParams(request, batch);

		if (this.requestParams != null) {
			params.addAll(this.requestParams);
		}

		request.setParameters(params);
		request.setMethod(this.method);
		return request;
	}

	protected Collection<TaskQueueParamPair> makeRequestParams(TaskQueuePushRequest request,
	                                                           List<ModelKey> keys) {
		List<TaskQueueParamPair> pairs = new ArrayList<TaskQueueParamPair>();

		TaskQueueParamPair pair = TaskQueueParamPair.pairWithCommaSeparatedValue(this.keysParamName, keys);
		pairs.add(pair);

		return pairs;
	}

	public Integer getMaxKeysPerRequest() {
		return this.maxKeysPerRequest;
	}

	public void setMaxKeysPerRequest(Integer maxKeysPerRequest) throws IllegalArgumentException {
		if (maxKeysPerRequest == null || maxKeysPerRequest < 1) {
			throw new IllegalArgumentException("Max Keys Per Request must be not null and greater than 0.");
		}

		this.maxKeysPerRequest = maxKeysPerRequest;
	}

	public List<TaskQueueParamPair> getRequestParams() {
		return this.requestParams;
	}

	public void setRequestParams(List<TaskQueueParamPair> requestParams) {
		this.requestParams = requestParams;
	}

	public String getKeysParamName() {
		return this.keysParamName;
	}

	public void setKeysParamName(String keysParamName) throws IllegalArgumentException {
		if (keysParamName == null || keysParamName.isEmpty()) {
			throw new IllegalArgumentException("Keys Param Name may be not null or empty.");
		}

		this.keysParamName = keysParamName;
	}

	public String getRequestUri() {
		return this.requestUri;
	}

	public void setRequestUri(String requestUri) throws IllegalArgumentException {
		if (requestUri == null || requestUri.isEmpty()) {
			throw new IllegalArgumentException("The Request URI may be not null or empty.");
		}

		this.requestUri = requestUri;
	}

	public Method getMethod() {
		return this.method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

}
