package com.dereekb.gae.server.taskqueue.system.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.server.taskqueue.deprecated.system.TaskRequestSystem;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.exception.SubmitTaskException;
import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskAlreadyExistsException;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * Implementation of {@link TaskRequestSystem}.
 *
 * @author dereekb
 *
 */
@Deprecated
public class TaskRequestSystemImpl
        implements TaskRequestSystem {

	/**
	 * Specific queue to submit requests to.
	 */
	private String queue;

	/**
	 * Optional filter used to filter out similar requests.
	 *
	 * It should be noted that this filter is to be used <i><b>only</b></i> for
	 * filtering out repeat/similar requests to prevent redundant calls. Do not
	 * used this filter to filter out requests that are undesirable, as no
	 * exception is raised and the system does not know what requests have been
	 * excluded.
	 */
	public Filter<TaskRequest> filter;

	/**
	 * Used to convert requests.
	 */
	public DirectionalConverter<TaskRequest, TaskOptions> taskConverter;

	public TaskRequestSystemImpl() {}

	public TaskRequestSystemImpl(Filter<TaskRequest> filter) {
		this(filter, null);
	}

	public TaskRequestSystemImpl(Filter<TaskRequest> filter,
	        DirectionalConverter<TaskRequest, TaskOptions> taskConverter) {
		this(filter, taskConverter, null);
	}

	public TaskRequestSystemImpl(Filter<TaskRequest> filter,
	        DirectionalConverter<TaskRequest, TaskOptions> taskConverter,
	        String queue) {
		this.setFilter(filter);
		this.setTaskConverter(taskConverter);
		this.setQueue(queue);
	}

	public String getQueue() {
		return this.queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public Filter<TaskRequest> getFilter() {
		return this.filter;
	}

	public void setFilter(Filter<TaskRequest> filter) {
		this.filter = filter;
	}

	public DirectionalConverter<TaskRequest, TaskOptions> getTaskConverter() {
		return this.taskConverter;
	}

	public void setTaskConverter(DirectionalConverter<TaskRequest, TaskOptions> taskConverter) {
		this.taskConverter = taskConverter;
	}

	@Override
	public void submitRequest(TaskRequest request) throws SubmitTaskException, TaskAlreadyExistsException {
		this.submitRequests(SingleItem.withValue(request));
	}

	@Override
	public void submitRequests(Collection<TaskRequest> requests) throws SubmitTaskException, TaskAlreadyExistsException {
		Collection<TaskRequest> filtered = this.filter(requests);

		if (filtered.isEmpty() == false) {
			try {
				List<TaskOptions> options = this.taskConverter.convert(filtered);

				Queue queue = this.getTaskQueue();
				queue.add(options);
			} catch (Exception e) {
				throw new SubmitTaskException(e);
			}
		}

	}

	private Collection<TaskRequest> filter(Collection<TaskRequest> requests) {

		if (this.filter != null) {
			FilterResults<TaskRequest> results = this.filter.filterObjects(requests);
			requests = results.getPassingObjects();
		}

		return requests;
	}

	private Queue getTaskQueue() {
		Queue queue = null;

		if (this.queue == null) {
			queue = QueueFactory.getDefaultQueue();
		} else {
			queue = QueueFactory.getQueue(this.queue);
		}

		return queue;
	}

	@Override
	public String toString() {
		return "TaskRequestSystemImpl [queue=" + this.queue + ", filter=" + this.filter + ", taskConverter="
		        + this.taskConverter + "]";
	}

}
