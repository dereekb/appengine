package com.dereekb.gae.model.extension.taskqueue.request;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.builder.TaskRequestBuilder;
import com.dereekb.gae.server.taskqueue.builder.TaskRequestCopier;
import com.dereekb.gae.server.taskqueue.system.TaskParameter;
import com.dereekb.gae.server.taskqueue.system.TaskParameterImpl;
import com.dereekb.gae.server.taskqueue.system.TaskRequest;
import com.dereekb.gae.server.taskqueue.system.TaskRequestImpl;
import com.dereekb.gae.utilities.collections.batch.BatchGenerator;

/**
 * Implementation of {@link TaskRequestBuilder} that generates tasks keyed to
 * {@link ModelKey} of the input {@link UniqueModel} instances.
 *
 * This builder uses a {@link TaskRequest} template to copy from, and appends
 * the request identifiers.
 *
 * This builder also batches
 *
 * @author dereekb
 *
 * @param <T>
 */
public class ModelKeyTaskRequestBuilder<T extends UniqueModel>
        implements TaskRequestBuilder<T> {

	public static final String DEFAULT_IDENTIFIER_PARAM_NAME = "keys";

	/**
	 * The parameter name to use.
	 */
	private String idParameter = DEFAULT_IDENTIFIER_PARAM_NAME;

	/**
	 * Whether or not individual requests (one for each {@link ModelKey}) should
	 * be generated, as opposed to batching.
	 */
	private boolean asIndividualRequests;

	/**
	 * The base request to copy.
	 */
	private TaskRequest baseRequest;

	/**
	 * The {@link TaskRequestCopier} to use for copying for each element.
	 */
	private TaskRequestCopier<TaskRequestImpl> copier;

	/**
	 * Internally-used batch genereator.
	 */
	private final BatchGenerator<ModelKey> batchGenerator = new BatchGenerator<ModelKey>();

	public ModelKeyTaskRequestBuilder() {}

	public ModelKeyTaskRequestBuilder(TaskRequest baseRequest) {
		this.baseRequest = baseRequest;
	}

	public String getIdParameter() {
		return this.idParameter;
	}

	public void setIdParameter(String idParameter) {
		this.idParameter = idParameter;
	}

	public boolean isAsIndividualRequests() {
		return this.asIndividualRequests;
	}

	public void setAsIndividualRequests(boolean asIndividualRequests) {
		this.asIndividualRequests = asIndividualRequests;
	}

	public Integer getBatchSize() {
		return this.batchGenerator.getBatchSize();
	}

	public void setBatchSize(Integer maxBatchSize) {
		this.batchGenerator.setBatchSize(maxBatchSize);
	}

	public TaskRequest getBaseRequest() {
		return this.baseRequest;
	}

	public void setBaseRequest(TaskRequest baseRequest) {
		this.baseRequest = baseRequest;
	}

	public TaskRequestCopier<TaskRequestImpl> getCopier() {
		return this.copier;
	}

	public void setCopier(TaskRequestCopier<TaskRequestImpl> copier) {
		this.copier = copier;
	}

	@Override
	public List<TaskRequest> buildRequests(Iterable<T> input) {
		List<ModelKey> keys = ModelKey.readModelKeys(input);
		return this.buildRequestsForKeys(keys);
	}

	public List<TaskRequest> buildRequestsForKeys(Iterable<ModelKey> input) {
		List<TaskRequest> requests;

		if (this.asIndividualRequests) {
			requests = this.buildMultiRequests(input);
		} else {
			requests = this.buildRequestBatches(input);
		}

		return requests;
	}

	public List<TaskRequest> buildRequestBatches(Iterable<ModelKey> input) {
		List<List<ModelKey>> keyBatches = this.batchGenerator.createBatches(input);
		List<TaskRequest> requests = new ArrayList<TaskRequest>();

		for (List<ModelKey> keyBatch : keyBatches) {
			TaskRequest request = this.buildRequestForBatch(keyBatch);
			requests.add(request);
		}

		return requests;
	}

	public TaskRequest buildRequestForBatch(Iterable<ModelKey> input) {
		List<String> keys = ModelKey.keysAsStrings(input);
		TaskParameterImpl keyParameter = TaskParameterImpl.parametersWithCommaSeparatedValue(this.idParameter, keys);
		TaskRequestImpl request = this.createNewModelKeyRequest(keyParameter);
		return request;
	}

	public List<TaskRequest> buildMultiRequests(Iterable<ModelKey> input) {
		List<String> keys = ModelKey.keysAsStrings(input);
		List<TaskParameterImpl> keyParameters = TaskParameterImpl.makeParametersForValues(this.idParameter, keys);

		List<TaskRequest> requests = new ArrayList<TaskRequest>();

		for (TaskParameterImpl keyParameter : keyParameters) {
			TaskRequest request = this.createNewModelKeyRequest(keyParameter);
			requests.add(request);
		}

		return requests;
	}

	private TaskRequestImpl createNewModelKeyRequest(TaskParameterImpl keyParameter) {
		TaskRequestImpl request = this.copier.fullyCopyRequest(this.baseRequest);
		Collection<TaskParameter> parameters = request.getParameters();

		if (this.baseRequest.getParameters() != null) {
			parameters = new ArrayList<TaskParameter>();
			parameters.addAll(request.getParameters());
		}

		request.setParameters(parameters);
		return request;
	}

}
