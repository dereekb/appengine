package com.dereekb.gae.utilities.collections.batch.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.dereekb.gae.utilities.collections.batch.Batch;
import com.dereekb.gae.utilities.collections.batch.CollectionPartitioner;
import com.dereekb.gae.utilities.collections.batch.Partition;

/**
 * {@link Batch} implementation.
 * <p>
 * {@link BatchImpl} instances are constructed using the
 * {@link BatchImplBuilder}.
 *
 * @author dereekb
 *
 * @param <T>
 *            element type
 */
public class BatchImpl<T>
        implements Batch<T> {

	private int size = 0;
	private final int maxBatchSize;
	private final List<BatchImplPartition> partitions = new ArrayList<BatchImplPartition>();

	private BatchImpl(int maxBatchSize) {
		this.maxBatchSize = maxBatchSize;
	}

	// MARK: Batch

	// MARK: Iterator
	@Override
	public Iterator<Partition<T>> iterator() {
		List<Partition<T>> temp = new ArrayList<Partition<T>>(this.partitions);
		return temp.iterator();
	}

	@Override
	public int getMaxBatchSize() {
		return this.maxBatchSize;
	}

	@Override
	public int getBatchCount() {
		return this.partitions.size();
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public Partition<T> getPartition(int index) throws IndexOutOfBoundsException {
		return this.partitions.get(index);
	}

	// MARK: Internal
	/**
	 * Adds a new {@link Partition} value to this {@link BatchImpl}.
	 *
	 * @param collection
	 * @see {@link BatchImplBuilder} for usage.
	 */
	private void addPartition(Collection<T> collection) {
		int index = this.partitions.size();
		BatchImplPartition partition = new BatchImplPartition(index, collection);
		this.partitions.add(partition);
	}

	// MARK: Builder
	/**
	 * Used for generating new {@link BatchImpl} instances.
	 *
	 * @author dereekb
	 *
	 */
	public static class BatchImplBuilder<T> {

		private List<T> elements = new ArrayList<T>();
		private final CollectionPartitioner partitioner = new CollectionPartitioner();

		public BatchImplBuilder() {}

		public BatchImplBuilder(int size) {
			this.setPartitionSize(size);
		}

		public BatchImplBuilder(Collection<T> elements) {
			this.setElements(elements);
		}

		public int getPartitionSize() {
			return this.partitioner.getPartitionSize();
		}

		public void setPartitionSize(int size) throws IllegalArgumentException {
			this.partitioner.setPartitionSize(size);
		}

		// MARK: Insertion
		public void setElements(Collection<T> elements) {
			this.elements = new ArrayList<T>(elements);
		}

		public void add(T element) {
			this.elements.add(element);
		}

		public void addAll(Collection<T> elements) {
			this.elements.addAll(elements);
		}

		// MARK: Batching
		public BatchImpl<T> batch() {
			BatchImpl<T> batch = new BatchImpl<T>(this.getPartitionSize());
			List<List<T>> partitions = this.partitioner.partitionsWithCollection(this.elements);

			for (List<T> partition : partitions) {
				batch.addPartition(partition);
			}

			return batch;
		}

	}

	// MARK: Partition
	/**
	 * {@link Partition} instance held by {@link BatchImpl}.
	 *
	 * @author dereekb
	 *
	 */
	private class BatchImplPartition
	        implements Partition<T> {

		private final int index;
		private final Collection<T> elements;

		public BatchImplPartition(int index, Collection<T> elements) {
			this.index = index;
			this.elements = elements;
		}

		// MARK: Partition
		@Override
		public Batch<T> getBatch() {
			return BatchImpl.this;
		}

		@Override
		public int getPartitionIndex() {
			return this.index;
		}

		@Override
		public Collection<T> getPartitionElements() {
			return this.elements;
		}

		@Override
		public String toString() {
			return "BatchImplPartition [index=" + this.index + ", elements=" + this.elements + "]";
		}

	}

	@Override
	public String toString() {
		return "BatchImpl [size=" + this.size + ", maxBatchSize=" + this.maxBatchSize + ", partitions="
		        + this.partitions + "]";
	}

}
