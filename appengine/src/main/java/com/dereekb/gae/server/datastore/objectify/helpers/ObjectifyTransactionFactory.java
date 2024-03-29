package com.dereekb.gae.server.datastore.objectify.helpers;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.utilities.collections.batch.Partitioner;
import com.googlecode.objectify.Work;

/**
 * Objectify transaction factory.
 * 
 * @author dereekb
 *
 */
public interface ObjectifyTransactionFactory {

	public ObjectifyTransactionType getTransactionType();

	// MARK: Transaction
	public <X> List<X> doTransactionWithPartition(ModelKeyListAccessor<?> input,
	                                              PartitionDelegate<ModelKey, X> delegate);

	public <X> List<X> doTransactionWithPartition(ModelKeyListAccessor<?> input,
	                                              CleanupPartitionDelegate<ModelKey, X> delegate);

	public <T, X> List<X> doTransactionWithPartition(Iterable<T> input,
	                                                 PartitionDelegate<T, X> delegate);

	public <T, X> List<X> doTransactionWithPartition(Iterable<T> input,
	                                                 CleanupPartitionDelegate<T, X> delegate);

	public <X> List<X> doTransactionWithPartition(ModelKeyListAccessor<?> input,
	                                              PartitionDelegate<ModelKey, X> delegate,
	                                              Integer partitionSize);

	public <X> List<X> doTransactionWithPartition(ModelKeyListAccessor<?> input,
	                                              CleanupPartitionDelegate<ModelKey, X> delegate,
	                                              Integer partitionSize);

	public <T, X> List<X> doTransactionWithPartition(Iterable<T> input,
	                                                 PartitionDelegate<T, X> delegate,
	                                                 Integer partitionSize);

	public <T, X> List<X> doTransactionWithPartition(Iterable<T> input,
	                                                 CleanupPartitionDelegate<T, X> delegate,
	                                                 Integer partitionSize);

	public <T, X> List<X> doTransaction(Iterable<T> input,
	                                    Partitioner partitioner,
	                                    PartitionDelegate<T, X> delegate);

	public <T, X> List<X> doTransaction(Iterable<T> input,
	                                    Partitioner partitioner,
	                                    CleanupPartitionDelegate<T, X> delegate);

	public <T, X> X doTransaction(Iterable<T> input,
	                              PartitionDelegate<T, X> delegate);

	public <X> X doTransaction(Work<X> work);

	public void doTransaction(Runnable work);

}
