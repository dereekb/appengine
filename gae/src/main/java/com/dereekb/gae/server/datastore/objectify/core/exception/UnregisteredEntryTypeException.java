package com.dereekb.gae.server.datastore.objectify.core.exception;

import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;

/**
 * Exception thrown when a requested type has not yet been registered by the
 * {@link ObjectifyDatabase}.
 *
 * @author dereekb
 *
 */
public final class UnregisteredEntryTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final Class<?> type;

	public UnregisteredEntryTypeException(Class<?> type) {
		super("The entity for '" + type + "' has not been registered.");
		this.type = type;
	}

	public Class<?> getType() {
		return this.type;
	}

	@Override
	public String toString() {
		return "UnregisteredEntryTypeException [type=" + this.type + "]";
	}

}
