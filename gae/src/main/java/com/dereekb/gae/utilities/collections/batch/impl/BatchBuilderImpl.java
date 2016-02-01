package com.dereekb.gae.utilities.collections.batch.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.dereekb.gae.utilities.collections.batch.Batch;
import com.dereekb.gae.utilities.collections.batch.BatchBuilder;
import com.dereekb.gae.utilities.collections.batch.Partition;

/**
 * {@link BatchBuilder} implementation.
 *
 * @author dereekb
 *
 */
public class BatchBuilderImpl
        implements BatchBuilder {

	private PartitionerImpl partitioner = new PartitionerImpl();

	public BatchBuilderImpl() throws IllegalArgumentException {
		this(100);
	}

	public BatchBuilderImpl(int partitionSize) throws IllegalArgumentException {
		this.setPartitionSize(partitionSize);
	}

	public PartitionerImpl getPartitioner() {
		return this.partitioner;
	}

	public void setPartitioner(PartitionerImpl partitioner) {
		this.partitioner = partitioner;
	}

	public int getPartitionSize() {
		return this.partitioner.getPartitionSize();
	}

	public void setPartitionSize(int size) throws IllegalArgumentException {
		this.partitioner.setPartitionSize(size);
	}

	// MARK: Batching
	@Override
	public <T> Batch<T> makeBatchWithCollection(Collection<T> collection) {
		BatchImpl<T> batch = new BatchImpl<T>(this.getPartitionSize());
		List<List<T>> partitions = this.partitioner.makePartitionsWithCollection(collection);

		for (List<T> partition : partitions) {
			batch.addPartition(partition);
		}

		return batch;
	}

	@Override
	public <T> Batch<T> makeBatch(Iterable<T> iterable) {
		BatchImpl<T> batch = new BatchImpl<T>(this.getPartitionSize());
		List<List<T>> partitions = this.partitioner.makePartitions(iterable);

		for (List<T> partition : partitions) {
			batch.addPartition(partition);
		}

		return batch;
	}

	// MARK: Internal
	private static class BatchImpl<T>
	        implements Batch<T> {

		private final int maxPartitionSize;
		private int size;
		private List<Partition<T>> partitions = new ArrayList<Partition<T>>();

		public BatchImpl(int maxBatchSize) {
			this.maxPartitionSize = maxBatchSize;
		}

		public void addPartition(List<T> partition) {
			this.size += partition.size();
			int index = this.partitions.size();
			BatchPartition batchPartition = new BatchPartition(index, partition);
			this.partitions.add(batchPartition);
		}

		// MARK: Batch Implementation
		@Override
		public Iterator<Partition<T>> iterator() {
			return this.partitions.iterator();
		}

		@Override
		public int getMaxPartitionSize() {
			return this.maxPartitionSize;
		}

		@Override
		public int getPartitionCount() {
			return this.partitions.size();
		}

		@Override
		public int getTotalSize() {
			return this.size;
		}

		@Override
		public Partition<T> getPartition(int index) throws IndexOutOfBoundsException {
			return this.partitions.get(index);
		}

		private class BatchPartition
		        implements Partition<T> {

			private final int partitionIndex;
			private final List<T> partitionElements;

			public BatchPartition(int partitionIndex, List<T> partitionElements) {
				this.partitionIndex = partitionIndex;
				this.partitionElements = partitionElements;
			}

			@Override
			public Batch<T> getBatch() {
				return BatchImpl.this;
			}

			@Override
			public int getPartitionIndex() {
				return this.partitionIndex;
			}

			@Override
			public List<T> getPartitionElements() {
				return this.partitionElements;
			}

			@Override
			public Iterator<T> iterator() {
				return this.partitionElements.iterator();
			}

			@Override
			public int getPartitionSize() {
				return this.partitionElements.size();
			}

			@Override
			public String toString() {
				return "BatchPartition [partitionIndex=" + this.partitionIndex + ", partitionElements="
				        + this.partitionElements + "]";
			}

		}

	}

}
