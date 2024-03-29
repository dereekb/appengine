package com.dereekb.gae.model.crud.services.exception;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.IteratorUtility;
import com.dereekb.gae.utilities.collections.SingleItem;

/**
 * Exception for when an atomic operations fails.
 * <p>
 * Optionally includes information about what element caused the operation to
 * fail.
 *
 * @author dereekb
 */
public class AtomicOperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String type;
	private Iterable<? extends UniqueModel> failed;
	private final AtomicOperationExceptionReason reason;
	private Exception exception;

	public AtomicOperationException() {
		this.exception = null;
		this.reason = AtomicOperationExceptionReason.ATOMIC_FAILURE;
	}

	/**
	 * Copy constructor.
	 */
	protected AtomicOperationException(AtomicOperationException e) {
		this.type = e.getType();
		this.failed = e.getFailed();
		this.reason = e.getReason();
		this.exception = e.getException();
	}

	public AtomicOperationException(UniqueModel failed, AtomicOperationExceptionReason reason) {
		this(SingleItem.withValue(failed), reason);
	}

	public AtomicOperationException(Iterable<? extends UniqueModel> failed, AtomicOperationExceptionReason reason) {
		super(String.format("Models were were unavailable for reason: %s", reason));
		this.failed = failed;
		this.reason = reason;
	}

	public AtomicOperationException(String message, AtomicOperationExceptionReason reason) {
		super(message);
		this.failed = Collections.emptyList();
		this.reason = reason;
	}

	public AtomicOperationException(UniqueModel failed, Exception exception) {
		this.failed = SingleItem.withValue(failed);
		this.reason = AtomicOperationExceptionReason.EXCEPTION;
		this.exception = exception;
	}

	public AtomicOperationException(String message, UniqueModel failed, AtomicOperationExceptionReason reason) {
		super(message);
		this.failed = SingleItem.withValue(failed);
		this.reason = reason;
		this.exception = null;
	}

	public AtomicOperationException(String message,
	        Iterable<? extends UniqueModel> failed,
	        AtomicOperationExceptionReason reason) {
		super(message);
		this.failed = failed;
		this.reason = reason;
		this.exception = null;
	}

	public AtomicOperationException(Iterable<? extends UniqueModel> failed, Exception exception) {
		this(exception);
		this.setFailed(failed);
	}

	public AtomicOperationException(Exception e) throws IllegalArgumentException {
		super(e);

		if (e == null) {
			throw new IllegalArgumentException("Exception cannot be null.");
		}

		this.exception = e;
		this.reason = AtomicOperationExceptionReason.EXCEPTION;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Exception getException() {
		return this.exception;
	}

	public AtomicOperationExceptionReason getReason() {
		return this.reason;
	}

	public Iterable<? extends UniqueModel> getFailed() {
		return this.failed;
	}

	public void setFailed(Iterable<? extends UniqueModel> failed) {
		this.failed = failed;
	}

	/**
	 * Returns the list of identifiers if they are available.
	 *
	 * @return
	 */
	public List<? extends UniqueModel> getUnavailableList() {
		return IteratorUtility.iterableToList(this.failed);
	}

	public List<ModelKey> getUnavailableModelKeys() {
		return ModelKey.readModelKeys(this.failed);
	}

	public List<String> getUnavailableStringKeys() {
		return ModelKey.readStringKeys(this.failed);
	}

	// MARK: Override
	@Override
	public Throwable getCause() {
		return this.exception;
	}

}
