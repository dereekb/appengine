package com.dereekb.gae.server.datastore.objectify.helpers;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.utilities.collections.batch.Partitioner;
import com.dereekb.gae.utilities.collections.batch.impl.PartitionerImpl;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;

public class ObjectifyTransactionUtility {

	public static final int MAX_TRANSACTION_ELEMENTS = 25;

	private static Partitioner PARTITIONER = null;

	public static final Partitioner getTransactionElementsPartitioner() {
		if (PARTITIONER == null) {
			PARTITIONER = new PartitionerImpl(MAX_TRANSACTION_ELEMENTS);
		}

		return PARTITIONER;
	}

	// MARK:
	public static <X> List<X> doPartitionedTransactNew(ModelKeyListAccessor<?> input,
	                                                   PartitionDelegate<ModelKey, X> delegate) {
		List<ModelKey> keys = input.getModelKeys();
		return doTransactNewWithPartition(keys, delegate);
	}

	// MARK: Transaction
	public static <T, X> List<X> doTransactNewWithPartition(Iterable<T> input,
	                                                        PartitionDelegate<T, X> delegate) {
		Partitioner partitioner = getTransactionElementsPartitioner();
		List<List<T>> partitions = partitioner.makePartitions(input);

		List<X> results = new ArrayList<X>(partitions.size());

		for (List<T> partition : partitions) {
			X result = doTransactNew(partition, delegate);
			results.add(result);
		}

		return results;
	}

	public static <T, X> List<X> doTransactNew(Iterable<T> input,
	                                           Partitioner partitioner,
	                                           PartitionDelegate<T, X> delegate) {
		List<List<T>> partitions = partitioner.makePartitions(input);

		List<X> results = new ArrayList<X>(partitions.size());

		for (List<T> partition : partitions) {
			X result = doTransactNew(partition, delegate);
			results.add(result);
		}

		return results;
	}

	public static <T, X> X doTransactNew(Iterable<T> input,
	                                     PartitionDelegate<T, X> delegate) {
		Work<X> work = delegate.makeWorkForInput(input);
		return ObjectifyService.ofy().transactNew(work);
	}

	public static <X> X doTransactNew(Work<X> work) {
		return ObjectifyService.ofy().transactNew(work);
	}

	public static interface PartitionDelegate<T, X> {

		public Work<X> makeWorkForInput(Iterable<T> input);

	}

}
