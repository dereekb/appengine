package com.dereekb.gae.web.api.server.schedule.exception;

import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown when a requested task is unavailable.
 * 
 * @author dereekb
 *
 */
public class UnavailableSchedulerTaskException extends ApiSafeRuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "UNAVAILABLE_SCHEDULER_TASK";
	public static final String ERROR_TITLE = "Unavailable Scheduler Task";

	private String name;

	public UnavailableSchedulerTaskException(String name) {
		super("The task with name '" + name + "' is unavailable.");
		this.setName(name);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("name cannot be null.");
		}

		this.name = name;
	}

	@Override
	public ApiResponseError asResponseError() {
		ApiResponseErrorImpl error = new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE);
		error.setData(this.name);
		return error;
	}

	@Override
	public String toString() {
		return "UnavailableSchedulerTaskException [name=" + this.name + "]";
	}

}
