package com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.MutableTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestBuilder;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestCopier;
import com.dereekb.gae.utilities.collections.batch.Partitioner;
import com.dereekb.gae.utilities.collections.batch.impl.PartitionerImpl;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;

/**
 * Abstract class that partitions input to break into different requests.
 * <p>
 * Does not implement {@link TaskRequestBuilder} directly as the
 * sub-implementations may differ about the generic value.
 * 
 * @author dereekb
 * @see AbstractTaskRequestBuilder
 */
public abstract class PartitionedTaskRequestBuilder<T> {

	protected static final Integer DEFAULT_PARTITION_SIZE = 40;

	private static final Partitioner DEFAULT_PARTITIONER = new PartitionerImpl(DEFAULT_PARTITION_SIZE);

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
	 * Partitioner used for splitting up the objects.
	 */
	private Partitioner partitioner;

	protected PartitionedTaskRequestBuilder(TaskRequest baseRequest) throws IllegalArgumentException {
		this(baseRequest, false);
	}

	protected PartitionedTaskRequestBuilder(TaskRequest baseRequest, boolean asIndividualRequests)
	        throws IllegalArgumentException {
		this.setAsIndividualRequests(asIndividualRequests);
		this.setBaseRequest(baseRequest);
		this.setDefaultPartitioner();
	}

	public boolean isAsIndividualRequests() {
		return this.asIndividualRequests;
	}

	public void setAsIndividualRequests(boolean asIndividualRequests) {
		this.asIndividualRequests = asIndividualRequests;
	}

	public TaskRequest getBaseRequest() {
		return this.baseRequest;
	}

	public void setBaseRequest(TaskRequest baseRequest) throws IllegalArgumentException {
		if (baseRequest == null) {
			throw new IllegalArgumentException("Base task request cannot be null.");
		}

		this.baseRequest = baseRequest;
	}

	public TaskRequestCopier<TaskRequestImpl> getCopier() {
		return this.copier;
	}

	public void setCopier(TaskRequestCopier<TaskRequestImpl> copier) throws IllegalArgumentException {
		if (copier == null) {
			throw new IllegalArgumentException("Copier cannot be null.");
		}

		this.copier = copier;
	}

	public Partitioner getPartitioner() {
		return this.partitioner;
	}

	public void setPartitioner(Partitioner partitioner) throws IllegalArgumentException {
		if (partitioner == null && this.asIndividualRequests == false) {
			throw new IllegalArgumentException("Partitioner is required when individual requests are false.");
		}

		this.partitioner = partitioner;
	}

	protected void setDefaultPartitioner() {
		this.setPartitioner(this.createDefaultPartitioner());
	}

	protected Partitioner createDefaultPartitioner() {
		return DEFAULT_PARTITIONER;
	}

	// MARK: TaskRequestBuilder
	public List<MutableTaskRequest> buildRequestsWithInput(Iterable<T> input) {
		List<MutableTaskRequest> requests = null;

		if (this.asIndividualRequests) {
			requests = this.buildIndividualRequests(input);
		} else {
			requests = this.buildRequestPartitions(input);
		}

		return requests;
	}

	protected List<MutableTaskRequest> buildRequestPartitions(Iterable<T> input) {
		List<List<T>> partitions = this.partitioner.makePartitions(input);
		return this.buildRequestsForPartitions(partitions);
	}

	protected List<MutableTaskRequest> buildIndividualRequests(Iterable<T> input) {
		List<List<T>> partitions = PartitionerImpl.SINGLE_OBJECT_PARTITIONER.makePartitions(input);
		return this.buildRequestsForPartitions(partitions);
	}

	protected List<MutableTaskRequest> buildRequestsForPartitions(List<List<T>> partitions) {
		List<MutableTaskRequest> requests = new ArrayList<MutableTaskRequest>();

		for (List<T> partition : partitions) {
			MutableTaskRequest request = this.buildRequest(partition);
			requests.add(request);
		}

		return requests;
	}

	protected TaskRequestImpl buildRequest(List<T> partition) {
		TaskRequestImpl request = this.copier.fullyCopyRequest(this.baseRequest);

		// Merge and set parameters.
		Collection<? extends KeyedEncodedParameter> parameters = this.buildRequestParameters(partition);
		request.mergeParameters(parameters);

		return request;
	}

	protected abstract Collection<? extends KeyedEncodedParameter> buildRequestParameters(List<T> partition);

}
