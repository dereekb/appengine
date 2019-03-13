package com.dereekb.gae.utilities.collections.batch;

import java.util.Collection;

/**
 * An immutable container type generated by partitioning items from a
 * {@link Collection} or {@link Iterable} value into smaller {@link Collection}
 * instances, all will the same maximum number of elements.
 *
 * @author dereekb
 *
 * @param <T>
 *            type of element in the batch.
 */
public interface Batch<T>
        extends Iterable<Partition<T>> {

	/**
	 * Returns the number of items per partition.
	 *
	 * @return {@code int} value containing the maximum number of values in a
	 *         partition.
	 */
	public int getMaxPartitionSize();

	/**
	 * Returns the number of batches.
	 *
	 * @return {@code int} value containing the number of partitions in this
	 *         batch.
	 */
	public int getPartitionCount();

	/**
	 * Returns the total number of elements in this {@link Batch}.
	 *
	 * @return {@code int} value containing the number of elements in the batch.
	 */
	public int getTotalSize();

	/**
	 * Returns the batch at the specified index.
	 *
	 * @param index
	 *            positive {@code int} value containing the index.
	 * @return {@link Collection} containing the items partitioned at the
	 *         {@code index}.
	 * @throws IndexOutOfBoundsException
	 *             if {@code index} is out of bounds.
	 */
	public Partition<T> getPartition(int index) throws IndexOutOfBoundsException;

}