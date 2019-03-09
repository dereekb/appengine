package com.dereekb.gae.server.taskqueue.deprecated;

import java.util.ArrayList;
import java.util.Collection;

import com.dereekb.gae.server.taskqueue.system.TaskRequest;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Push request that is used for creating new tasks for the task queue.
 *
 * @author dereekb
 * @see TaskQueueManager
 * @deprecated Replaced by {@link TaskRequest}
 */
@Deprecated
public class TaskQueuePushRequest {

	/**
	 * Optional, specific task name.
	 */
	private String name;

	/**
	 * The Request URL
	 */
	private String requestUrl;

	/**
	 * Date/Time at which to start this queue.
	 */
	private Long eta;

	/**
	 * Countdown for when to start the request.
	 */
	private Long countdown;

	/**
	 * Method type. POST/PUT/DELETE
	 */
	private Method method;

	/**
	 * Request Parameters
	 */
	private Collection<TaskQueueParamPair> parameters;

	/**
	 * Request Headers
	 */
	private Collection<TaskQueueParamPair> headers;

	public TaskQueuePushRequest(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public TaskQueuePushRequest copy() {
		return this.copy(false);
	}

	public TaskQueuePushRequest copy(boolean copyAll) {
		TaskQueuePushRequest copy = new TaskQueuePushRequest(this.requestUrl);

		copy.setEta(this.eta);
		copy.setMethod(this.method);
		copy.setCountdown(this.countdown);

		if (copyAll) {
			copy.setName(this.name);
			copy.setParameters(this.parameters);
		}

		return copy;
	}

	public Collection<TaskQueueParamPair> getParameters() {
		return this.parameters;
	}

	public void setParameters(Collection<TaskQueueParamPair> pairs) {
		this.parameters = pairs;
	}

	public Collection<TaskQueueParamPair> getHeaders() {
		return this.headers;
	}

	public void setHeaders(Collection<TaskQueueParamPair> headers) {
		this.headers = headers;
	}

	public void addParameter(TaskQueueParamPair pair) {
		if (this.parameters == null) {
			this.parameters = new ArrayList<TaskQueueParamPair>();
		}

		this.parameters.add(pair);
	}

	public void addHeader(TaskQueueParamPair pair) {
		if (this.headers == null) {
			this.headers = new ArrayList<TaskQueueParamPair>();
		}

		this.headers.add(pair);
	}

	public Integer getParametersHashcode() {
		int result = 1;

		if (this.parameters != null) {
			for (TaskQueueParamPair pair : this.parameters) {
				result = result + pair.hashCode();
			}
		}

		return result;
	}

	public String getRequestUrl() {
		return this.requestUrl;
	}

	public void setRequestUrl(String request) throws NullPointerException {
		if (request == null) {
			throw new NullPointerException();
		}

		this.requestUrl = request;
	}

	public Long getEta() {
		return this.eta;
	}

	public void setEta(Long eta) {
		this.eta = eta;
	}

	public Long getCountdown() {
		return this.countdown;
	}

	public void setCountdown(Long countdown) throws IllegalArgumentException {
		if (countdown != null && countdown < 0) {
			throw new IllegalArgumentException("Cannot have a negative countdown.");
		}

		this.countdown = countdown;
	}

	public Method getMethod() {
		return this.method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getParametersHashcode();
		result = prime * result + ((this.requestUrl == null) ? 0 : this.requestUrl.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "TaskQueuePushRequest [name=" + this.name + ", requestUrl=" + this.requestUrl + ", eta=" + this.eta
		        + ", countdown=" + this.countdown + ", method=" + this.method + ", parameters=" + this.parameters
		        + ", headers="
		        + this.headers + "]";
	}

}
