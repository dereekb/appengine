package com.dereekb.gae.server.taskqueue.system;

import java.util.Collection;

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

	private String url;

	private Collection<TaskParameter> headers;

	private Collection<TaskParameter> parameters;

	private TaskRequestTiming timings;

	public TaskRequestImpl() {}

	public TaskRequestImpl(String url) {
		this.url = url;
	}

	public TaskRequestImpl(String url, Collection<TaskParameter> headers, TaskRequestTiming timings) {
		this.url = url;
		this.headers = headers;
		this.timings = timings;
	}

	public TaskRequestImpl(String name,
	        Method method,
	        String url,
	        Collection<TaskParameter> headers,
	        Collection<TaskParameter> parameters,
	        TaskRequestTiming timings) {
		this.name = name;
		this.method = method;
		this.url = url;
		this.headers = headers;
		this.parameters = parameters;
		this.timings = timings;
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
		this.method = method;
	}

	@Override
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
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
		result = prime * result + ((this.url == null) ? 0 : this.url.hashCode());
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
		if (this.url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!this.url.equals(other.url)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "TaskRequestImpl [name=" + this.name + ", method=" + this.method + ", url=" + this.url + ", headers="
		        + this.headers + ", parameters=" + this.parameters + ", timings=" + this.timings + "]";
	}

}
