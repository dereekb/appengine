package com.dereekb.gae.utilities.collections.batch;

import java.util.Collection;

/**
 * Partition in a {@link Batch}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface Partition<T> {

	/**
	 * Returns the {@link Batch} this {@link Partition} is a part of.
	 *
	 * @return {@link Batch} this is a part of. Never {@code null}.
	 */
	public Batch<T> getBatch();

	/**
	 * The partition index in the {@link Batch}.
	 *
	 * @return {@code int} value for the index.
	 */
	public int getPartitionIndex();

	/**
	 * Returns the values in the partition.
	 *
	 * @return {@link Collection} containing values. Never {@code null}.
	 */
	public Collection<T> getPartitionElements();

}
