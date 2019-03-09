package com.dereekb.gae.utilities.collections.batch;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * {@link Partitioner} extension that allows variable cuts.
 * 
 * @author dereekb
 *
 */
public interface ExtendedPartitioner
        extends Partitioner {

	/**
	 * Creates a partition of elements using a {@link Collection}.
	 *
	 * @param elements
	 *            {@link Collection}. Never {@code null}.
	 * @param size
	 *            {@link Integer}. {@code null} values will use value from
	 *            {@link #getDefaultPartitionSize()}.
	 * @return {@link List} containing another list of partitions.
	 */
	public <T> List<List<T>> makePartitionsWithCollection(Collection<T> iterable,
	                                                      Integer size)
	        throws IllegalArgumentException;

	/**
	 * Creates partitions of elements using the values within
	 * the passed
	 * {@link Iterable} value.
	 *
	 * @param iterable
	 *            {@link Iterable}. Never {@code null}.
	 * @param size
	 *            {@link Integer}. {@code null} values will use value from
	 *            {@link #getDefaultPartitionSize()}.
	 * @return {@link List} containing another list of partitions.
	 */
	public <T> List<List<T>> makePartitions(Iterable<T> iterable,
	                                        Integer size)
	        throws IllegalArgumentException;

	/**
	 * Creates partitions of elements using the values within the passed
	 * {@link Iterator} value.
	 *
	 * @param iterator
	 *            {@link Iterator}. Never {@code null}.
	 * @param size
	 *            {@link Integer}. {@code null} values will use value from
	 *            {@link #getDefaultPartitionSize()}.
	 * @return {@link List} containing another list of partitions.
	 */
	public <T> List<List<T>> makePartitions(Iterator<T> iterator,
	                                        Integer size)
	        throws IllegalArgumentException;

	/**
	 * Cuts a partition of elements using the input {@link Iterator}.
	 *
	 * @param iterator
	 *            {@link Iterator}. Never {@code null}.
	 * @param size
	 *            {@link Integer}. {@code null} values will use value from
	 *            {@link #getDefaultPartitionSize()}.
	 * @return {@link List} containing the partition.
	 */
	public <T> List<T> cutPartition(Iterator<T> iterator,
	                                Integer size)
	        throws IllegalArgumentException;

}
