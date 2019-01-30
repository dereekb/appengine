package com.dereekb.gae.server.datastore.objectify.helpers;

import com.googlecode.objectify.Work;

/**
 * {@link ObjectifyTransactionFactory} delegate.
 * 
 * @author dereekb
 *
 * @param <T>
 *            input type
 * @param <X>
 *            output type
 */
public interface PartitionDelegate<T, X> {

	public Work<X> makeWorkForInput(Iterable<T> input);

}