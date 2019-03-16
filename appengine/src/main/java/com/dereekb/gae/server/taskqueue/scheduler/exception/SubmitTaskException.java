package com.dereekb.gae.server.taskqueue.scheduler.exception;

import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown by {@link TaskRequestService} when a task cannot be submitted.
 *
 * @author dereekb
 *
 */
public class SubmitTaskException extends ApiSafeRuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "SUBMIT_TASK_ERROR";
	public static final String ERROR_TITLE = "Submit Task Error";

	public SubmitTaskException() {
		super();
	}

	public SubmitTaskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SubmitTaskException(String message, Throwable cause) {
		super(message, cause);
	}

	public SubmitTaskException(String message) {
		super(message);
	}

	public SubmitTaskException(Throwable cause) {
		super(cause);
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		ApiResponseErrorImpl error = new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE);
		
		// TODO: Consider adding more data?
		error.setDetail(this.getMessage());
		
		return error;
	}

	@Override
	public String toString() {
		return "SubmitTaskException []";
	}

}
