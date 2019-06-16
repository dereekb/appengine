package com.dereekb.gae.server.taskqueue.scheduler.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.server.taskqueue.scheduler.MutableTaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequestDataType;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequestTiming;
import com.dereekb.gae.server.taskqueue.scheduler.utility.converter.TaskRequestHost;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.misc.keyed.utility.KeyedUtility;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.impl.KeyedEncodedParameterImpl;
import com.dereekb.gae.utilities.misc.path.SimplePath;
import com.dereekb.gae.utilities.misc.path.impl.SimplePathImpl;

/**
 * {@link TaskRequest} implementation.
 *
 * @author dereekb
 *
 */
public class TaskRequestImpl
        implements MutableTaskRequest {

	private TaskRequestDataType dataType = TaskRequestDataType.PARAMETERS;

	private String name;

	private SimplePath path;

	private Collection<KeyedEncodedParameter> headers;

	private Collection<KeyedEncodedParameter> securityHeaders;

	private Collection<KeyedEncodedParameter> parameters;

	private String requestData;

	private TaskRequestTiming timings;

	private TaskRequestHost host;

	public TaskRequestImpl() {}

	public TaskRequestImpl(String path) {
		this(path, null);
	}

	public TaskRequestImpl(String path, TaskRequestTiming timings) {
		this(path, timings, null);
	}

	public TaskRequestImpl(String path,
	        TaskRequestTiming timings,
	        Collection<KeyedEncodedParameter> headers) {
		this(null, path, timings, headers, null);
	}

	public TaskRequestImpl(String name,
	        String path,
	        TaskRequestTiming timings,
	        Collection<KeyedEncodedParameter> headers,
	        Collection<KeyedEncodedParameter> parameters) {
		this.setName(name);
		this.setPath(path);
		this.setTimings(timings);
		this.setHeaders(headers);
		this.setParameters(parameters);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public SimplePath getPath() {
		return this.path;
	}

	public void setPath(String path) throws IllegalArgumentException {
		this.setPath(new SimplePathImpl(path));
	}

	@Override
	public void setPath(SimplePath path) throws IllegalArgumentException {
		if (path == null) {
			throw new IllegalArgumentException("Path cannot be null.");
		}

		this.path = path;
	}

	@Override
	public Collection<KeyedEncodedParameter> getHeaders() {
		return this.headers;
	}

	@Override
	public void setHeaders(Collection<? extends KeyedEncodedParameter> headers) {
		this.headers = ListUtility.safeCopy(headers);
	}

	public void replaceHeader(KeyedEncodedParameter replacement) {
		this.headers = KeyedEncodedParameterImpl.replaceInCollection(this.headers, replacement);
	}

	@Override
	public Collection<KeyedEncodedParameter> getSecurityHeaders() {
		return this.securityHeaders;
	}

	@Override
	public void setSecurityHeaders(Collection<? extends KeyedEncodedParameter> headers) {
		this.securityHeaders = ListUtility.safeCopy(headers);
	}

	@Override
	public Collection<KeyedEncodedParameter> getParameters() {
		return this.parameters;
	}

	@Override
	public void setParameters(Collection<? extends KeyedEncodedParameter> parameters) {
		this.parameters = ListUtility.safeCopy(parameters);
	}

	public void setParameters(Map<String, String> parameters) {
		if (parameters != null) {
			List<KeyedEncodedParameterImpl> keyedParameters = KeyedEncodedParameterImpl
			        .makeParametersWithMap(parameters);
			this.setParameters(keyedParameters);
		} else {
			this.parameters = null;
		}
	}

	public void mergeParameters(Collection<? extends KeyedEncodedParameter> parameters) {
		this.setParameters(KeyedUtility.safeMerge(parameters, this.parameters));
	}

	public void replaceParameter(KeyedEncodedParameter replacement) {
		this.setParameters(KeyedEncodedParameterImpl.replaceInCollection(this.parameters, replacement));
	}

	@Override
	public TaskRequestDataType getDataType() {
		return this.dataType;
	}

	@Override
	public String getRequestData() {
		return this.requestData;
	}

	@Override
	public void setRequestData(String requestData) {
		this.setRequestData(TaskRequestDataType.JSON, requestData);
	}

	@Override
	public void setRequestData(TaskRequestDataType dataType,
	                           String requestData) {
		if (requestData == null) {
			dataType = TaskRequestDataType.PARAMETERS;
		}

		this.dataType = dataType;
		this.requestData = requestData;
	}

	@Override
	public TaskRequestTiming getTimings() {
		return this.timings;
	}

	@Override
	public void setTimings(TaskRequestTiming timings) {
		this.timings = timings;
	}

	@Override
	public TaskRequestHost getHost() {
		return this.host;
	}

	@Override
	public void setHost(TaskRequestHost host) {
		this.host = host;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.dataType == null) ? 0 : this.dataType.hashCode());
		result = prime * result + ((this.headers == null) ? 0 : this.headers.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.parameters == null) ? 0 : this.parameters.hashCode());
		result = prime * result + ((this.path == null) ? 0 : this.path.hashCode());
		result = prime * result + ((this.requestData == null) ? 0 : this.requestData.hashCode());
		result = prime * result + ((this.timings == null) ? 0 : this.timings.hashCode());
		result = prime * result + ((this.host == null) ? 0 : this.host.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		TaskRequestImpl other = (TaskRequestImpl) obj;
		if (this.dataType != other.dataType) {
			return false;
		}
		if (this.headers == null) {
			if (other.headers != null) {
				return false;
			}
		} else if (!this.headers.equals(other.headers)) {
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
		if (this.path == null) {
			if (other.path != null) {
				return false;
			}
		} else if (!this.path.equals(other.path)) {
			return false;
		}
		if (this.requestData == null) {
			if (other.requestData != null) {
				return false;
			}
		} else if (!this.requestData.equals(other.requestData)) {
			return false;
		}
		if (this.timings == null) {
			if (other.timings != null) {
				return false;
			}
		} else if (!this.timings.equals(other.timings)) {
			return false;
		}
		if (this.host == null) {
			if (other.host != null) {
				return false;
			}
		} else if (!this.host.equals(other.host)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "TaskRequestImpl [name=" + this.name + ", path=" + this.path + ", headers="
		        + this.headers + ", parameters=" + this.parameters + ", timings=" + this.timings + "]";
	}

}
