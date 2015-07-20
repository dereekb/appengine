package com.dereekb.gae.web.api.taskqueue.controller.crud.exception;

import com.dereekb.gae.web.api.taskqueue.controller.crud.TaskQueueEditController;

/**
 * Thrown by {@link TaskQueueEditController} if a specified type is not
 * available.
 *
 * @author dereekb
 *
 */
public class UnregisteredEditTypeException
        extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnregisteredEditTypeException() {
		super();
	}

	public UnregisteredEditTypeException(String message) {
		super(message);
	}

	public UnregisteredEditTypeException(Throwable cause) {
		super(cause);
	}

}
