package com.dereekb.gae.server.taskqueue.scheduler.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.exception.SubmitTaskException;
import com.dereekb.gae.server.taskqueue.scheduler.utility.converter.TaskRequestConverter;
import com.dereekb.gae.server.taskqueue.scheduler.utility.converter.impl.TaskRequestConverterImpl;
import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskAlreadyExistsException;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * {@link TaskSchedulerContext} implementation.
 *
 * @author dereekb
 *
 */
public class TaskSchedulerImpl
        implements TaskScheduler {

	private Context context;

	public TaskSchedulerImpl(Filter<TaskRequest> filter) {
		this(filter, new TaskRequestConverterImpl());
	}

	public TaskSchedulerImpl(Filter<TaskRequest> filter, TaskRequestConverter converter) {
		this(null, filter, converter);
	}

	public TaskSchedulerImpl(String queue, Filter<TaskRequest> filter, TaskRequestConverter converter) {
		this.context = new Context(queue, filter, converter);
	}

	public String getQueue() {
		return this.context.getQueue();
	}

	public void setQueue(String queue) {
		this.context.setQueue(queue);
	}

	public Filter<TaskRequest> getFilter() {
		return this.context.getFilter();
	}

	public void setFilter(Filter<TaskRequest> filter) {
		this.context.setFilter(filter);
	}

	public TaskRequestConverter getConverter() {
		return this.context.getConverter();
	}

	public void setConverter(TaskRequestConverter converter) {
		this.context.setConverter(converter);
	}

	// MARK: TaskScheduler
	@Override
    public void schedule(TaskRequest request) throws SubmitTaskException, TaskAlreadyExistsException {
		this.context.schedule(request);
    }

	@Override
    public void schedule(Collection<TaskRequest> requests) throws SubmitTaskException, TaskAlreadyExistsException {
		this.context.schedule(requests);
    }

	// MARK: Context
	public Context makeContext(TaskRequestConverter converter) {
		return this.context.makeContext(converter);
	}

	public Context makeContext(String queue,
	                           Filter<TaskRequest> filter,
	                           TaskRequestConverter converter) {
		return this.context.makeContext(queue, filter, converter);
	}

	public class Context
	        implements TaskScheduler {

		private final Context parent;

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
		private Filter<TaskRequest> filter;

		/**
		 * Used to convert requests.
		 */
		private TaskRequestConverter converter;

		protected Context(String queue, Filter<TaskRequest> filter, TaskRequestConverter converter) {
			this(null, queue, filter, converter);
		}

		protected Context(Context parent,
		        String queue,
		        Filter<TaskRequest> filter,
		        TaskRequestConverter converter) {
			this.parent = parent;
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

		public Filter<TaskRequest> getFilter() {
			return this.filter;
		}

		public void setFilter(Filter<TaskRequest> filter) {
			this.filter = filter;
		}

		public TaskRequestConverter getConverter() {
			return this.converter;
		}

		public void setConverter(TaskRequestConverter converter) {
			if (converter == null && this.parent == null) {
				throw new IllegalArgumentException("Task Converter on root element cannot be null.");
			}

			this.converter = converter;
		}

		// MARK: Context
		public Context makeContext(TaskRequestConverter converter) {
			return new Context(this, null, null, converter);
		}

		public Context makeContext(String queue,
		                           Filter<TaskRequest> filter,
		                           TaskRequestConverter converter) {
			return new Context(this, queue, filter, converter);
		}

		// MARK: Contextual Accessor
		public String getContextQueue() {
			String queue = this.queue;

			if (queue == null && this.parent != null) {
				queue = this.parent.getContextQueue();
			}

			return queue;
		}

		public Filter<TaskRequest> getContextFilter() {
			Filter<TaskRequest> filter = this.filter;

			if (filter == null) {
				filter = this.parent.getContextFilter();
			}

			return filter;
		}

		public TaskRequestConverter getContextConverter() {
			TaskRequestConverter converter = this.converter;

			if (converter == null) {
				converter = this.parent.getContextConverter();
			}

			return converter;
		}

		// MARK: TaskScheduler
		@Override
		public void schedule(TaskRequest request) throws SubmitTaskException, TaskAlreadyExistsException {
			this.schedule(SingleItem.withValue(request));
		}

		@Override
		public void schedule(Collection<TaskRequest> requests) throws SubmitTaskException, TaskAlreadyExistsException {
			Collection<TaskRequest> filtered = this.filter(requests);

			if (filtered.isEmpty() == false) {
				try {
					TaskRequestConverter converter = this.getContextConverter();
					List<TaskOptions> options = converter.convert(filtered);
					Queue queue = this.getTaskQueue();
					queue.add(options);
				} catch (Exception e) {
					throw new SubmitTaskException(e);
				}
			}

		}

		// MARK: Internal
		private Collection<TaskRequest> filter(Collection<TaskRequest> requests) {
			Filter<TaskRequest> filter = this.getContextFilter();

			if (filter != null) {
				FilterResults<TaskRequest> results = filter.filterObjects(requests);
				requests = results.getPassingObjects();
			}

			return requests;
		}

		private Queue getTaskQueue() {
			String queueName = this.getContextQueue();
			Queue queue = null;

			if (queueName == null) {
				queue = QueueFactory.getDefaultQueue();
			} else {
				queue = QueueFactory.getQueue(queueName);
			}

			return queue;
		}

	}

}