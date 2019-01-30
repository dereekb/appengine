package com.dereekb.gae.utilities.collections.batch;

import java.util.Collection;
import java.util.Iterator;

/**
 * Used for generating {@link Batch} instances.
 *
 * @author dereekb
 */
public interface BatchBuilder {

	public <T> Batch<T> makeBatchWithCollection(Collection<T> collection);

	public <T> Batch<T> makeBatch(Iterable<T> iterable);

	public <T> Batch<T> makeBatch(Iterator<T> iterator);

}
