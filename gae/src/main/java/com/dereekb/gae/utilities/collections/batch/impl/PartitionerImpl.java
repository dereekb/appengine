package com.dereekb.gae.utilities.collections.batch.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.dereekb.gae.utilities.collections.batch.Partitioner;

/**
 * {@link Partitioner} implementation.
 * 
 * @author dereekb
 *
 */
public class PartitionerImpl
        implements Partitioner {

	private static final Integer DEFAULT_PARTITION_SIZE = 100;

	private int partitionSize = DEFAULT_PARTITION_SIZE;

	public PartitionerImpl() {}

	public PartitionerImpl(int partitionSize) throws IllegalArgumentException {
		this.setPartitionSize(partitionSize);
	}

	@Override
    public int getPartitionSize() {
		return this.partitionSize;
	}

	public void setPartitionSize(int partitionSize) throws IllegalArgumentException {
		if ((partitionSize > 0) == false) {
			throw new IllegalArgumentException("Partition size should be greater than zero.");
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
		List<List<T>> partitions = new ArrayList<List<T>>();

		Iterator<T> iterator = elements.iterator();
		while (iterator.hasNext()) {
			List<T> currentBatch = this.cutPartition(iterator);
			partitions.add(currentBatch);
		}

		return partitions;
	}

	@Override
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
		return "PartitionerImpl [partitionSize=" + this.partitionSize + "]";
	}

}
