package com.dereekb.gae.utilities.collections.batch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

//TODO: Rename to BatchGeneratorImpl, and create a BatchGenerator interface.

/**
 * Used for breaking up a collection of items into several lists, or batches.
 *
 * @author dereekb
 */
public class BatchGenerator<T> {

	private static final Integer DEFAULT_BATCH_SIZE = 100;

	private Integer batchSize = DEFAULT_BATCH_SIZE;

	public BatchGenerator() {
		this(DEFAULT_BATCH_SIZE);
	}

	public BatchGenerator(Integer batchSize) {
		super();
		this.setBatchSize(batchSize);
	}

	public Integer getBatchSize() {
		return this.batchSize;
	}

	public void setBatchSize(Integer batchSize) throws NullPointerException, IllegalArgumentException {
		if (batchSize == null) {
			throw new NullPointerException("Batch size cannot be null.");
		} else if (batchSize <= 0) {
			throw new IllegalArgumentException("Batch size must be greater than 0.");
		}

		this.batchSize = batchSize;
	}

	public List<List<T>> createBatchesWithCollection(Collection<T> items) {
		List<List<T>> batches;

		if (items == null || items.isEmpty()) {
			batches = Collections.emptyList();
		} else {
			batches = this.createBatches(items);
		}

		return batches;
	}

	public List<List<T>> createBatches(Iterable<T> items) {
		List<List<T>> batches = new ArrayList<List<T>>();

		Iterator<T> iterator = items.iterator();
		while (iterator.hasNext()) {
			List<T> currentBatch = this.createBatch(iterator);
			batches.add(currentBatch);
		}

		return batches;
	}

	public List<T> createBatch(Iterator<T> iterator) {
		List<T> batch = new ArrayList<T>();

		for (int i = 0; (i < this.batchSize && iterator.hasNext()); i += 1) {
			T item = iterator.next();
			batch.add(item);
		}

		return batch;
	}

	public static <T> List<List<T>> createBatches(Collection<T> items,
	                                              Integer count) {
		BatchGenerator<T> generator = new BatchGenerator<T>(count);
		return generator.createBatchesWithCollection(items);
	}

}
