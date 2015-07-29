package com.dereekb.gae.model.crud.function.exception;

import com.dereekb.gae.model.crud.function.delegate.DeleteFunctionDelegate;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Used by {@link DeleteFunctionDelegate} to signify that the delete should be
 * canceled.
 *
 * @author dereekb
 *
 */
@Deprecated
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
