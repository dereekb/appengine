package com.dereekb.gae.server.taskqueue.scheduler.impl;

import java.net.URI;
import java.util.Collection;

import com.dereekb.gae.server.taskqueue.scheduler.TaskParameter;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequestTiming;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * {@link TaskRequest} implementation.
 *
 * @author dereekb
 *
 */
public class TaskRequestImpl
        implements TaskRequest {

	private String name;

	private Method method;

	private URI uri;

	private Collection<TaskParameter> headers;

	private Collection<TaskParameter> parameters;

	private TaskRequestTiming timings;

	public TaskRequestImpl() {}

	public TaskRequestImpl(URI uri) {
		this(uri, null);
	}

	public TaskRequestImpl(URI uri, Method method) {
		this(uri, method, null);
	}

	public TaskRequestImpl(URI uri, Method method, TaskRequestTiming timings) {
		this(uri, method, timings, null);
	}

	public TaskRequestImpl(URI uri, TaskRequestTiming timings, Collection<TaskParameter> headers) {
		this(uri, Method.PUT, timings, headers);
	}

	public TaskRequestImpl(URI uri, Method method, TaskRequestTiming timings, Collection<TaskParameter> headers) {
		this(null, method, uri, timings, headers, null);
	}

	public TaskRequestImpl(String name,
	        Method method,
	        URI uri,
	        TaskRequestTiming timings,
	        Collection<TaskParameter> headers,
	        Collection<TaskParameter> parameters) {
		this.setName(name);
		this.setMethod(method);
		this.setUri(uri);
		this.setTimings(timings);
		this.setHeaders(headers);
		this.setParameters(parameters);
	}

	@Override
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Method getMethod() {
		return this.method;
	}

	public void setMethod(Method method) {
		if (method == null) {
			method = Method.PUT;
		}

		this.method = method;
	}

	@Override
	public URI getUri() {
		return this.uri;
	}

	public void setUri(URI uri) throws IllegalArgumentException {
		if (uri == null) {
			throw new IllegalArgumentException("URI cannot be null.");
		}

		this.uri = uri;
	}

	@Override
	public Collection<TaskParameter> getHeaders() {
		return this.headers;
	}

	public void setHeaders(Collection<TaskParameter> headers) {
		this.headers = headers;
	}

	public void replaceHeader(TaskParameter replacement) {
		this.headers = TaskParameterImpl.replaceParameterInCollection(this.headers, replacement);
	}

	@Override
	public Collection<TaskParameter> getParameters() {
		return this.parameters;
	}

	public void setParameters(Collection<TaskParameter> parameters) {
		this.parameters = parameters;
	}

	public void replaceParameter(TaskParameter replacement) {
		this.parameters = TaskParameterImpl.replaceParameterInCollection(this.parameters, replacement);
	}

	@Override
	public TaskRequestTiming getTimings() {
		return this.timings;
	}

	public void setTimings(TaskRequestTiming timings) {
		this.timings = timings;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.headers == null) ? 0 : this.headers.hashCode());
		result = prime * result + ((this.method == null) ? 0 : this.method.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.parameters == null) ? 0 : this.parameters.hashCode());
		result = prime * result + ((this.timings == null) ? 0 : this.timings.hashCode());
		result = prime * result + ((this.uri == null) ? 0 : this.uri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		TaskRequestImpl other = (TaskRequestImpl) obj;
		if (this.headers == null) {
			if (other.headers != null) {
				return false;
			}
		} else if (!this.headers.equals(other.headers)) {
			return false;
		}
		if (this.method != other.method) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.parameters == null) {
			if (other.parameters != null) {
				return false;
			}
		} else if (!this.parameters.equals(other.parameters)) {
			return false;
		}
		if (this.timings == null) {
			if (other.timings != null) {
				return false;
			}
		} else if (!this.timings.equals(other.timings)) {
			return false;
		}
		if (this.uri == null) {
			if (other.uri != null) {
				return false;
			}
		} else if (!this.uri.equals(other.uri)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "TaskRequestImpl [name=" + this.name + ", method=" + this.method + ", uri=" + this.uri + ", headers="
		        + this.headers + ", parameters=" + this.parameters + ", timings=" + this.timings + "]";
	}

}
