package com.dereekb.gae.server.datastore.utility;

/**
 * Thrown by {@link StagedUpdater} when attempting to call
 * {@link StagedUpdater#finishUpdate()} a second time.
 * 
 * @author dereekb
 *
 */
public class StagedUpdaterAlreadyFinishedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StagedUpdaterAlreadyFinishedException() {
		super();
	}

	public StagedUpdaterAlreadyFinishedException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public StagedUpdaterAlreadyFinishedException(String message, Throwable cause) {
		super(message, cause);
	}

	public StagedUpdaterAlreadyFinishedException(String message) {
		super(message);
	}

	public StagedUpdaterAlreadyFinishedException(Throwable cause) {
		super(cause);
	}

}
