package com.dereekb.gae.server.datastore.utility;

/**
 * Thrown by {@link StagedTransactionChange} when attempting to call
 * {@link StagedTransactionChange#finishChanges()} a second time.
 * 
 * @author dereekb
 *
 */
public class StagedTransactionAlreadyFinishedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StagedTransactionAlreadyFinishedException() {
		super();
	}

	public StagedTransactionAlreadyFinishedException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public StagedTransactionAlreadyFinishedException(String message, Throwable cause) {
		super(message, cause);
	}

	public StagedTransactionAlreadyFinishedException(String message) {
		super(message);
	}

	public StagedTransactionAlreadyFinishedException(Throwable cause) {
		super(cause);
	}

}
