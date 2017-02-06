package com.dereekb.gae.utilities.collections.batch;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Used for generating partitions of elements contained within a
 * {@link Collection} or {@link Iterable}.
 *
 * @author dereekb
 *
 */
public interface Partitioner {

	/**
	 * Size of each partition.
	 *
	 * @return Size of partitions to create.
	 */
	public int getDefaultPartitionSize();

	/**
	 * Creates a partition of elements using a {@link Collection}.
	 *
	 * @param elements
	 *            {@link Collection}. Never {@code null}.
	 * @return {@link List} containing another list of partitions.
	 */
	public <T> List<List<T>> makePartitionsWithCollection(Collection<T> iterable);

	/**
	 * Creates partitions of elements using the values within the passed
	 * {@link Iterable} value.
	 *
	 * @param iterable
	 *            {@link Iterable}. Never {@code null}.
	 * @return {@link List} containing another list of partitions.
	 */
	public <T> List<List<T>> makePartitions(Iterable<T> iterable);

	/**
	 * Creates partitions of elements using the values within the passed
	 * {@link Iterator} value.
	 *
	 * @param iterator
	 *            {@link Iterator}. Never {@code null}.
	 * @return {@link List} containing another list of partitions.
	 */
	public <T> List<List<T>> makePartitions(Iterator<T> iterator);

	/**
	 * Cuts a partition of elements using the input {@link Iterator}.
	 *
	 * @param iterator
	 *            {@link Iterator}. Never {@code null}.
	 * @return {@link List} containing the partition.
	 */
	public <T> List<T> cutPartition(Iterator<T> iterator);

}
