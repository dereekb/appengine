package com.dereekb.gae.utilities.collections.batch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Utility used for partitioning elements in {@link Collection} and
 * {@link Iterable} into {@link List} partitions, each with the same max size.
 *
 * @author dereekb
 *
 */
public class CollectionPartitioner {

	private static final Integer DEFAULT_PARTITION_SIZE = 100;

	private int partitionSize = DEFAULT_PARTITION_SIZE;

	public CollectionPartitioner() {}

	public CollectionPartitioner(int partitionSize) {
		this.setPartitionSize(partitionSize);
	}

	public int getPartitionSize() {
		return this.partitionSize;
	}

	public void setPartitionSize(int partitionSize) throws IllegalArgumentException {
		if (partitionSize < 0) {
			throw new IllegalArgumentException("Partition size should be greater than zero.");
		}

		this.partitionSize = partitionSize;
	}

	public <T> List<List<T>> partitionsWithCollection(Collection<T> elements) {
		List<List<T>> partitions;

		if (elements == null || elements.isEmpty()) {
			partitions = Collections.emptyList();
		} else {
			partitions = this.partitions(elements);
		}

		return partitions;
	}

	public <T> List<List<T>> partitions(Iterable<T> elements) {
		List<List<T>> partitions = new ArrayList<List<T>>();

		Iterator<T> iterator = elements.iterator();
		while (iterator.hasNext()) {
			List<T> currentBatch = this.cutPartition(iterator);
			partitions.add(currentBatch);
		}

		return partitions;
	}

	public <T> List<T> cutPartition(Iterator<T> iterator) {
		List<T> partition = new ArrayList<T>();

		for (int i = 0; (i < this.partitionSize && iterator.hasNext()); i += 1) {
			T element = iterator.next();
			partition.add(element);
		}

		return partition;
	}

	@Override
	public String toString() {
		return "CollectionPartitioner [partitionSize=" + this.partitionSize + "]";
	}

}
