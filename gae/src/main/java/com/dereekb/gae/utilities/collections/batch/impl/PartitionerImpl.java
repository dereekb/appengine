package com.dereekb.gae.utilities.collections.batch.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.dereekb.gae.utilities.collections.batch.ExtendedPartitioner;
import com.dereekb.gae.utilities.collections.batch.Partitioner;

/**
 * {@link ExtendedPartitioner} implementation.
 *
 * @author dereekb
 *
 */
public class PartitionerImpl
        implements ExtendedPartitioner {

	/**
	 * Singleton partitioner that creates partitions of size 1.
	 */
	public static final ExtendedPartitioner SINGLE_OBJECT_PARTITIONER = new PartitionerImpl(1);

	private static final Integer DEFAULT_PARTITION_SIZE = 100;

	private int defaultPartitionSize = DEFAULT_PARTITION_SIZE;

	private InternalPartitioner partitioner;

	public PartitionerImpl() {}

	public PartitionerImpl(int partitionSize) throws IllegalArgumentException {
		this.setDefaultPartitionSize(partitionSize);
	}

	@Override
	public int getDefaultPartitionSize() {
		return this.partitioner.partitionSize;
	}

	public void setDefaultPartitionSize(int partitionSize) throws IllegalArgumentException {
		this.partitioner = new InternalPartitioner(partitionSize);
	}

	private static void validatePartitionSize(int partitionSize) {
		if ((partitionSize > 0) == false) {
			throw new IllegalArgumentException("Partition size should be greater than zero.");
		}
	}

	@Override
	public <T> List<List<T>> makePartitionsWithCollection(Collection<T> iterable) {
		return this.partitioner.makePartitionsWithCollection(iterable);
	}

	@Override
	public <T> List<List<T>> makePartitions(Iterable<T> iterable) {
		return this.partitioner.makePartitions(iterable);
	}

	@Override
	public <T> List<List<T>> makePartitions(Iterator<T> iterator) {
		return this.partitioner.makePartitions(iterator);
	}

	@Override
	public <T> List<T> cutPartition(Iterator<T> iterator) {
		return this.partitioner.cutPartition(iterator);
	}

	// MARK: Variable Partitioner
	@Override
	public <T> List<List<T>> makePartitionsWithCollection(Collection<T> iterable,
	                                                      Integer size)
	        throws IllegalArgumentException {
		return new InternalPartitioner(size).makePartitionsWithCollection(iterable);
	}

	@Override
	public <T> List<List<T>> makePartitions(Iterable<T> iterable,
	                                        Integer size)
	        throws IllegalArgumentException {
		return new InternalPartitioner(size).makePartitions(iterable);
	}

	@Override
	public <T> List<List<T>> makePartitions(Iterator<T> iterator,
	                                        Integer size)
	        throws IllegalArgumentException {
		return new InternalPartitioner(size).makePartitions(iterator);
	}

	@Override
	public <T> List<T> cutPartition(Iterator<T> iterator,
	                                Integer size)
	        throws IllegalArgumentException {
		return new InternalPartitioner(size).cutPartition(iterator);
	}

	private class InternalPartitioner
	        implements Partitioner {

		private int partitionSize;

		public InternalPartitioner(int partitionSize) {
			this.setPartitionSize(partitionSize);
		}

		@Override
		public int getDefaultPartitionSize() {
			return this.partitionSize;
		}

		public void setPartitionSize(Integer partitionSize) {
			if (partitionSize == null) {
				partitionSize = PartitionerImpl.this.defaultPartitionSize;
			} else {
				validatePartitionSize(partitionSize);
			}

			this.partitionSize = partitionSize;
		}

		@Override
		public <T> List<List<T>> makePartitionsWithCollection(Collection<T> elements) {
			List<List<T>> partitions;

			if (elements == null || elements.isEmpty()) {
				partitions = Collections.emptyList();
			} else {
				partitions = this.makePartitions(elements);
			}

			return partitions;
		}

		@Override
		public <T> List<List<T>> makePartitions(Iterable<T> elements) {
			Iterator<T> iterator = elements.iterator();
			return this.makePartitions(iterator);
		}

		@Override
		public <T> List<List<T>> makePartitions(Iterator<T> iterator) {
			List<List<T>> partitions = new ArrayList<List<T>>();

			while (iterator.hasNext()) {
				List<T> currentBatch = this.cutPartition(iterator);
				partitions.add(currentBatch);
			}

			return partitions;
		}

		@Override
		public <T> List<T> cutPartition(Iterator<T> iterator) {
			List<T> partition = new ArrayList<T>(this.partitionSize);

			for (int i = 0; (i < this.partitionSize && iterator.hasNext()); i += 1) {
				T element = iterator.next();
				partition.add(element);
			}

			return partition;
		}

	}

}
