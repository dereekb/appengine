package com.dereekb.gae.server.datastore.utility;

/**
 * {@link StagedTransactionChange} result.
 * 
 * @author dereekb
 *
 * @param <T>
 *            result type
 * @see StagedTransactionChange for returning a "void" result.
 */
public interface StagedTransactionChangeResult<T>
        extends StagedTransactionChange {

	/**
	 * Returns the resulting value.
	 * 
	 * @return Result. May be {@code null}.
	 */
	public T getResult();

}
