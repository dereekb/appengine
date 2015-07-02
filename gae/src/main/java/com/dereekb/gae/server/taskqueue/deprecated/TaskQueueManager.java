package com.dereekb.gae.server.taskqueue.deprecated;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskAlreadyExistsException;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.common.base.Joiner;

/**
 * Primary class for submitting requests to the Google App Engine Task Queue
 * API.
 *
 * @author dereekb
 */
@Deprecated
public class TaskQueueManager {

	private final static String TASK_URL_REQUEST_FORMAT = "/taskqueue/%s";

	/**
	 * Name of the queue to submit requests to.
	 */
	private String queueName = null;

	private String baseUrl = null;

	private Long defaultCountdown = null;

	private Method defaultMethod = Method.PUT;

	private Filter<TaskQueuePushRequest> filter;

	private boolean ignoreTaskExistExceptions = false;

	public TaskQueueManager() {
		super();
	}

	private Queue getTaskQueue() {
		Queue queue = null;

		if (this.queueName == null) {
			queue = QueueFactory.getDefaultQueue();
		} else {
			queue = QueueFactory.getQueue(this.queueName);
		}

		return queue;
	}

	public TaskOptions buildTaskOption(TaskQueuePushRequest request) {
		TaskQueueRequestBuilder builder = new TaskQueueRequestBuilder(request);
		TaskOptions options = builder.buildTaskOptions();
		return options;
	}

	/**
	 * Adds a new task to the queue, built from the given request.
	 *
	 * @param request
	 */
	public boolean add(TaskQueuePushRequest request) throws NullPointerException, TaskAlreadyExistsException {
		if (request == null) {
			throw new NullPointerException();
		}

		SingleItem<TaskQueuePushRequest> single = SingleItem.withValue(request);
		return this.add(single);
	}

	/**
	 * Adds a set of new tasks to the queue, built from the given requests collection.
	 *
	 * @param request
	 */
	public boolean add(Iterable<TaskQueuePushRequest> requests) throws TaskAlreadyExistsException {
		boolean success = true;
		List<TaskOptions> options = new ArrayList<TaskOptions>();
		requests = this.filterRequests(requests);

		for (TaskQueuePushRequest request : requests) {
			TaskOptions option = this.buildTaskOption(request);
			options.add(option);
		}

		if (options.isEmpty() == false) {
			Queue taskQueue = this.getTaskQueue();

			try {
				taskQueue.add(options);
			} catch (TaskAlreadyExistsException e) {
				if (this.ignoreTaskExistExceptions == false) {
					throw e;
				}
				success = false;
			}
		}

		return success;
	}

	private Iterable<TaskQueuePushRequest> filterRequests(Iterable<TaskQueuePushRequest> requests) {
		Iterable<TaskQueuePushRequest> acceptableRequests = requests;

		if (this.filter != null) {
			FilterResults<TaskQueuePushRequest> filterResults = this.filter.filterObjects(requests);
			acceptableRequests = filterResults.getPassingObjects();
		}

		return acceptableRequests;
	}

	public void purgeQueue() {
		// TODO: Purge/Delete all tasks
	}

	public void removeTasks(String task) {
		// TODO: Remove task
	}

	private class TaskQueueRequestBuilder {

		private final TaskQueuePushRequest request;

		public TaskQueueRequestBuilder(TaskQueuePushRequest request) {
			super();
			this.request = request;
		}

		public String getTaskName() {
			String name = this.request.getName();

			/*
			 * TODO: Removed, since the Task Queue does not immediately delete names of tasks that have been cleared.
			 * Task names can take up to 7 days, so using the task queue to avoid rapid updating of a target is not ideal for now.
			 * In the future, can think of a way to make hash codes that also incorporate periods of time.
			 * See: https://developers.google.com/appengine/docs/java/taskqueue/#Java_Task_names
			 */
			// if (name == null) {
			// Integer hashcode = request.hashCode();
			// name = hashcode.toString();
			// }

			return name;
		}

		public String getFullUrl() {
			String requestUrl = this.request.getRequestUrl();

			Joiner joiner = Joiner.on("/").skipNulls();
			String compoundUrl = joiner.join(TaskQueueManager.this.baseUrl, requestUrl);

			String fullUrl = String.format(TASK_URL_REQUEST_FORMAT, compoundUrl);
			return fullUrl;
		}

		private Method getTaskMethod() {
			Method method = this.request.getMethod();

			if (method == null) {
				method = TaskQueueManager.this.defaultMethod;
			}

			return method;
		}

		private TaskOptions newTaskOptions() {
			String url = this.getFullUrl();
			String name = this.getTaskName();

			Method method = this.getTaskMethod();
			TaskOptions baseOptions = TaskOptions.Builder.withUrl(url).method(method).taskName(name);
			Long eta = this.request.getEta();

			if (eta != null) {
				baseOptions = baseOptions.etaMillis(eta);
			} else {
				Long countdown = this.request.getCountdown();

				if (countdown != null) {
					baseOptions = baseOptions.countdownMillis(countdown);
				} else if (TaskQueueManager.this.defaultCountdown != null) {
					baseOptions = baseOptions.countdownMillis(TaskQueueManager.this.defaultCountdown);
				}
			}

			return baseOptions;
		}

		private TaskOptions appendParameters(TaskOptions options) {
			Collection<TaskQueueParamPair> parameters = this.request.getParameters();
			TaskOptions newOptions = options;

			if (parameters != null) {
				for (TaskQueueParamPair pair : parameters) {
					String param = pair.getParameter();
					String value = pair.getValue();
					newOptions = newOptions.param(param, value);
				}
			}

			return newOptions;
		}

		private TaskOptions appendHeaders(TaskOptions options) {
			Collection<TaskQueueParamPair> headers = this.request.getHeaders();
			TaskOptions newOptions = options;

			if (headers != null) {
				for (TaskQueueParamPair pair : headers) {
					String param = pair.getParameter();
					String value = pair.getValue();
					newOptions = newOptions.header(param, value);
				}
			}

			return newOptions;
		}

		public TaskOptions buildTaskOptions() {
			TaskOptions options = this.newTaskOptions();
			options = this.appendHeaders(options);
			options = this.appendParameters(options);
			return options;
		}
	}

	public String getQueueName() {
		return this.queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getBaseUrl() {
		return this.baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public Long getDefaultCountdown() {
		return this.defaultCountdown;
	}

	public void setDefaultCountdown(Long countdown) throws IllegalArgumentException {
		if (countdown != null && countdown < 0) {
			throw new IllegalArgumentException("Cannot have a negative countdown.");
		}

		this.defaultCountdown = countdown;
	}

	public Method getDefaultMethod() {
		return this.defaultMethod;
	}

	public void setDefaultMethod(Method defaultMethod) throws IllegalArgumentException {
		if (defaultMethod == null) {
			throw new IllegalArgumentException("Default Method cannot be null.");
		}

		this.defaultMethod = defaultMethod;
	}

	public boolean isIgnoreTaskExistExceptions() {
		return this.ignoreTaskExistExceptions;
	}

	public void setIgnoreTaskExistExceptions(boolean ignoreTaskExistExceptions) {
		this.ignoreTaskExistExceptions = ignoreTaskExistExceptions;
	}

	public Filter<TaskQueuePushRequest> getFilter() {
		return this.filter;
	}

	public void setFilter(Filter<TaskQueuePushRequest> filter) {
		this.filter = filter;
	}

}
