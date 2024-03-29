package com.dereekb.gae.model.crud.exception;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Exception used to signify that the delete should be canceled due to certain
 * models.
 *
 * @author dereekb
 *
 */
public class CancelDeleteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Iterable<? extends UniqueModel> rejected;

	public CancelDeleteException(Iterable<? extends UniqueModel> rejected) {
		this.rejected = rejected;
	}

	public CancelDeleteException(Iterable<? extends UniqueModel> rejected, Throwable cause) {
		super(cause);
		this.rejected = rejected;
	}

	public Iterable<? extends UniqueModel> getRejected() {
		return this.rejected;
	}

	public void setRejected(Iterable<? extends UniqueModel> rejected) {
		this.rejected = rejected;
	}

}
