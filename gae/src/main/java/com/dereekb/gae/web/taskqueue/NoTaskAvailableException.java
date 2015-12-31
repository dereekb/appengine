package com.dereekb.gae.web.taskqueue;

import com.dereekb.gae.model.extension.taskqueue.deprecated.api.CustomTaskInfo;

@Deprecated
public class NoTaskAvailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoTaskAvailableException() {
		super();
	}

	public NoTaskAvailableException(String message) {
		super(message);
	}

	public static NoTaskAvailableException forTask(String task,
	                                               CustomTaskInfo request) {
		String message = "No task available with name " + task + " with request: " + request;
		NoTaskAvailableException exception = new NoTaskAvailableException(message);
		return exception;
	}

}
