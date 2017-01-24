package com.dereekb.gae.model.extension.taskqueue.request;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestBuilder;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestCopier;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl.AbstractTaskRequestBuilder;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl.TaskRequestCopierImpl;
import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.utilities.collections.batch.impl.PartitionerImpl;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.impl.KeyedEncodedParameterImpl;

/**
 * Implementation of {@link TaskRequestBuilder} that generates tasks keyed to
 * {@link ModelKey} of the input {@link UniqueModel} instances.
 * <p>
 * This builder uses a {@link TaskRequest} template to copy from, and appends
 * the request identifiers.
 * <p>
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelKeyTaskRequestBuilder<T extends UniqueModel> extends AbstractTaskRequestBuilder<T> {

	public static final String DEFAULT_IDENTIFIER_PARAM_NAME = "keys";

	/**
	 * The parameter name to use.
	 */
	private String idParameter = DEFAULT_IDENTIFIER_PARAM_NAME;

	/**
	 * Whether or not individual requests (one for each {@link ModelKey}) should
	 * be generated, as opposed to batching.
	 */
	private boolean asIndividualRequests = false;

	/**
	 * The base request to copy.
	 */
	private TaskRequest baseRequest;

	/**
	 * The {@link TaskRequestCopier} to use for copying for each element.
	 */
	private TaskRequestCopier<TaskRequestImpl> copier = TaskRequestCopierImpl.SINGLETON;

	/**
	 * Internally-used batch genereator.
	 */
	private final PartitionerImpl partitioner = new PartitionerImpl();

	protected ModelKeyTaskRequestBuilder() {}

	public ModelKeyTaskRequestBuilder(TaskRequest baseRequest) {
		this.setBaseRequest(baseRequest);
	}

	public String getIdParameter() {
		return this.idParameter;
	}

	public void setIdParameter(String idParameter) {
		this.idParameter = idParameter;
	}

	@Override
	public boolean isAsIndividualRequests() {
		return this.asIndividualRequests;
	}

	@Override
	public void setAsIndividualRequests(boolean asIndividualRequests) {
		this.asIndividualRequests = asIndividualRequests;
	}

	public int getPartitionSize() {
		return this.partitioner.getDefaultPartitionSize();
	}

	public void setPartitionSize(Integer partitionSize) {
		this.partitioner.setDefaultPartitionSize(partitionSize);
	}

	@Override
	public TaskRequest getBaseRequest() {
		return this.baseRequest;
	}

	@Override
	public void setBaseRequest(TaskRequest baseRequest) {
		this.baseRequest = baseRequest;
	}

	@Override
	public TaskRequestCopier<TaskRequestImpl> getCopier() {
		return this.copier;
	}

	@Override
	public void setCopier(TaskRequestCopier<TaskRequestImpl> copier) {
		this.copier = copier;
	}

	// MARK: TaskRequestBuilder
	@Override
	protected Collection<KeyedEncodedParameter> buildRequestParameters(List<T> partition) {
		KeyedEncodedParameterImpl keyParameter = KeyedEncodedParameterImpl.make(this.idParameter, partition);
		return new SingleItem<KeyedEncodedParameter>(keyParameter);
	}

	@Override
	public String toString() {
		return "ModelKeyTaskRequestBuilder [idParameter=" + this.idParameter + ", asIndividualRequests="
		        + this.asIndividualRequests + ", baseRequest=" + this.baseRequest + ", copier=" + this.copier
		        + ", partitioner=" + this.partitioner + "]";
	}

}
