package com.dereekb.gae.server.taskqueue.scheduler.appengine;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.taskqueue.scheduler.SecuredTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskSchedulerEnqueuer;
import com.dereekb.gae.server.taskqueue.scheduler.appengine.utility.converter.AppEngineTaskRequestConverter;
import com.dereekb.gae.server.taskqueue.scheduler.appengine.utility.converter.impl.AppEngineTaskRequestConverterImpl;
import com.dereekb.gae.server.taskqueue.scheduler.exception.SubmitTaskException;
import com.dereekb.gae.server.taskqueue.scheduler.exception.TaskAlreadyExistsException;
import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * {@link TaskSchedulerEnqueuer} implementation for Google App Engine.
 *
 * @author dereekb
 *
 */
public class AppEngineTaskSchedulerEnqueuer
        implements TaskSchedulerEnqueuer {

	/**
	 * Specific queue to submit requests to.
	 */
	private String queue;

	/**
	 * Optional filter used to filter out similar requests.
	 * <p>
	 * It should be noted that this filter is to be used <i><b>only</b></i>
	 * for filtering out repeat/similar requests to prevent redundant calls.
	 * Do not used this filter to filter out requests that are undesirable,
	 * as no exception is raised and the system does not know what requests
	 * have been excluded.
	 */
	private Filter<SecuredTaskRequest> filter;

	private AppEngineTaskRequestConverter converter;

	public AppEngineTaskSchedulerEnqueuer() {
		this(new AppEngineTaskRequestConverterImpl());
	}

	public AppEngineTaskSchedulerEnqueuer(AppEngineTaskRequestConverter converter) {
		this(converter, null);
	}

	public AppEngineTaskSchedulerEnqueuer(AppEngineTaskRequestConverter converter, String queue) {
		this(converter, queue, null);
	}

	public AppEngineTaskSchedulerEnqueuer(AppEngineTaskRequestConverter converter, String queue, Filter<SecuredTaskRequest> filter) {
		this.setQueue(queue);
		this.setFilter(filter);
		this.setConverter(converter);
	}

	public String getQueue() {
		return this.queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public Filter<SecuredTaskRequest> getFilter() {
		return this.filter;
	}

	public void setFilter(Filter<SecuredTaskRequest> filter) {
		this.filter = filter;
	}

	public AppEngineTaskRequestConverter getConverter() {
		return this.converter;
	}

	public void setConverter(AppEngineTaskRequestConverter converter) {
		if (converter == null) {
			throw new IllegalArgumentException("Task Converter on root element cannot be null.");
		}

		this.converter = converter;
	}

	// MARK: TaskSchedulerEnqueuer
	@Override
	public void enqueue(SecuredTaskRequest request) throws SubmitTaskException, TaskAlreadyExistsException {
		this.enqueue(SingleItem.withValue(request));
	}

	@Override
	public void enqueue(Collection<? extends SecuredTaskRequest> requests)
	        throws SubmitTaskException,
	            TaskAlreadyExistsException {
		Collection<SecuredTaskRequest> filtered = this.filter(requests);

		if (filtered.isEmpty() == false) {
			try {
				List<TaskOptions> options = this.converter.convert(filtered);
				Queue queue = this.getTaskQueue();
				queue.add(options);
			} catch (Exception e) {
				throw new SubmitTaskException(e);
			}
		}
	}

	private Collection<SecuredTaskRequest> filter(Collection<? extends SecuredTaskRequest> requests) {
		if (this.filter != null) {
			FilterResults<SecuredTaskRequest> results = this.filter.filterObjects(requests);
			return results.getPassingObjects();
		} else {
			return ListUtility.safeCopy(requests);
		}
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

}
