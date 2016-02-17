package com.dereekb.gae.server.taskqueue.scheduler.utility.filter;

import com.dereekb.gae.server.taskqueue.scheduler.TaskParameter;
import com.dereekb.gae.utilities.filters.AbstractFilter;
import com.dereekb.gae.utilities.filters.FilterResult;

/**
 * Utility filter for filtering parameters by parameter and/or value.
 *
 * @author dereekb
 *
 */
public class TaskParameterFilter extends AbstractFilter<TaskParameter> {

	private String parameter;
	private String value;

	public TaskParameterFilter() {}

	public TaskParameterFilter(String parameter, String value) {
		this.parameter = parameter;
		this.value = value;
	}

	public String getParameter() {
		return this.parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public FilterResult filterObject(TaskParameter object) {
		boolean isOk = true;

		String parameter = object.getParameter();
		String value = object.getValue();

		if (this.value != null) {
			isOk = this.value.equals(value);
		}

		if (isOk && this.parameter != null) {
			isOk = this.parameter.equals(parameter);
		}

		return FilterResult.withBoolean(isOk);
	}

}
