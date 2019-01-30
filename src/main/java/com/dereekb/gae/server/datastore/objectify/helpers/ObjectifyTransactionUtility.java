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

	public static final ObjectifyTransactionFactory NEW_TRANSACTION_FACTORY = new ObjectifyNewTransactionFactoryImpl();
	public static final ObjectifyTransactionFactory DEFAULT_TRANSACTION_FACTORY = new ObjectifyDefaultTransactionFactoryImpl();

	// MARK: Accessors
	public static ObjectifyTransactionFactory transact() {
		return DEFAULT_TRANSACTION_FACTORY;
	}

	public static ObjectifyTransactionFactory newTransact() {
		return NEW_TRANSACTION_FACTORY;
	}

	public static ObjectifyTransactionFactory newTransact(int maxTries) {
		return new ObjectifyNewTransactionFactoryImpl(maxTries);
	}

	// MARK: Transactions
	@Deprecated
	public static <X> List<X> doPartitionedTransactNew(ModelKeyListAccessor<?> input,
	                                                   PartitionDelegate<ModelKey, X> delegate) {
		return NEW_TRANSACTION_FACTORY.doTransactionWithPartition(input, delegate);
	}

	@Deprecated
	public static <T, X> List<X> doTransactNewWithPartition(Iterable<T> input,
	                                                        PartitionDelegate<T, X> delegate) {
		return NEW_TRANSACTION_FACTORY.doTransactionWithPartition(input, delegate);
	}

	@Deprecated
	public static <T, X> List<X> doTransactNew(Iterable<T> input,
	                                           Partitioner partitioner,
	                                           PartitionDelegate<T, X> delegate) {
		return NEW_TRANSACTION_FACTORY.doTransaction(input, partitioner, delegate);
	}

	@Deprecated
	public static <T, X> X doTransactNew(Iterable<T> input,
	                                     PartitionDelegate<T, X> delegate) {
		return NEW_TRANSACTION_FACTORY.doTransaction(input, delegate);
	}

	@Deprecated
	public static <X> X doTransactNew(Work<X> work) {
		return NEW_TRANSACTION_FACTORY.doTransaction(work);
	}

	// MARK: Factories
	public static class ObjectifyDefaultTransactionFactoryImpl extends ObjectifyTransactionFactoryImpl {

		@Override
		public ObjectifyTransactionType getTransactionType() {
			return ObjectifyTransactionType.DEFAULT_TRANSACTION;
		}

		@Override
		public <X> X doTransaction(Work<X> work) {
			return ObjectifyService.ofy().transact(work);
		}

	}

	public static class ObjectifyNewTransactionFactoryImpl extends ObjectifyTransactionFactoryImpl {

		private final int maxTries;

		public ObjectifyNewTransactionFactoryImpl() {
			this(Integer.MAX_VALUE);
		}

		public ObjectifyNewTransactionFactoryImpl(int maxTries) {
			this.maxTries = maxTries;
		}

		public int getMaxTries() {
			return this.maxTries;
		}

		@Override
		public ObjectifyTransactionType getTransactionType() {
			return ObjectifyTransactionType.NEW_TRANSACTION;
		}

		@Override
		public <X> X doTransaction(Work<X> work) {
			return ObjectifyService.ofy().transactNew(this.maxTries, work);
		}

	}

	private static abstract class ObjectifyTransactionFactoryImpl
	        implements ObjectifyTransactionFactory {

		// MARK: ObjectifyTransactionFactory
		@Override
		public <X> List<X> doTransactionWithPartition(ModelKeyListAccessor<?> input,
		                                              PartitionDelegate<ModelKey, X> delegate) {
			List<ModelKey> keys = input.getModelKeys();
			return this.doTransactionWithPartition(keys, delegate);
		}

		@Override
		public <X> List<X> doTransactionWithPartition(ModelKeyListAccessor<?> input,
		                                              CleanupPartitionDelegate<ModelKey, X> delegate) {
			List<ModelKey> keys = input.getModelKeys();
			return this.doTransactionWithPartition(keys, delegate);
		}

		@Override
		public <T, X> List<X> doTransactionWithPartition(Iterable<T> input,
		                                                 PartitionDelegate<T, X> delegate) {
			Partitioner partitioner = getTransactionElementsPartitioner();
			return this.doTransaction(input, partitioner, delegate);
		}

		@Override
		public <T, X> List<X> doTransactionWithPartition(Iterable<T> input,
		                                                 CleanupPartitionDelegate<T, X> delegate) {
			Partitioner partitioner = getTransactionElementsPartitioner();
			return this.doTransaction(input, partitioner, delegate);
		}

		@Override
		public <X> List<X> doTransactionWithPartition(ModelKeyListAccessor<?> input,
		                                              PartitionDelegate<ModelKey, X> delegate,
		                                              Integer partitionSize) {
			List<ModelKey> keys = input.getModelKeys();
			Partitioner partitioner = new PartitionerImpl(partitionSize);
			return this.doTransaction(keys, partitioner, delegate);
		}

		@Override
		public <X> List<X> doTransactionWithPartition(ModelKeyListAccessor<?> input,
		                                              CleanupPartitionDelegate<ModelKey, X> delegate,
		                                              Integer partitionSize) {
			List<ModelKey> keys = input.getModelKeys();
			Partitioner partitioner = new PartitionerImpl(partitionSize);
			return this.doTransaction(keys, partitioner, delegate);
		}

		@Override
		public <T, X> List<X> doTransactionWithPartition(Iterable<T> input,
		                                                 PartitionDelegate<T, X> delegate,
		                                                 Integer partitionSize) {
			Partitioner partitioner = new PartitionerImpl(partitionSize);
			return this.doTransaction(input, partitioner, delegate);
		}

		@Override
		public <T, X> List<X> doTransactionWithPartition(Iterable<T> input,
		                                                 CleanupPartitionDelegate<T, X> delegate,
		                                                 Integer partitionSize) {
			Partitioner partitioner = new PartitionerImpl(partitionSize);
			return this.doTransaction(input, partitioner, delegate);
		}

		@Override
		public <T, X> List<X> doTransaction(Iterable<T> input,
		                                    Partitioner partitioner,
		                                    PartitionDelegate<T, X> delegate) {
			return doTransaction(input, partitioner, new CleanupPartitionDelegate<T, X>() {

				@Override
				public Work<X> makeWorkForInput(Iterable<T> input) {
					return delegate.makeWorkForInput(input);
				}

				@Override
				public X cleanup(X output) {
					return output;
				}

			});
		}

		@Override
		public <T, X> List<X> doTransaction(Iterable<T> input,
		                                    Partitioner partitioner,
		                                    CleanupPartitionDelegate<T, X> delegate) {
			List<List<T>> partitions = partitioner.makePartitions(input);

			List<X> results = new ArrayList<X>(partitions.size());

			for (List<T> partition : partitions) {
				X result = this.doTransaction(partition, delegate);
				result = delegate.cleanup(result);
				results.add(result);
			}

			return results;
		}

		@Override
		public <T, X> X doTransaction(Iterable<T> input,
		                              PartitionDelegate<T, X> delegate) {
			Work<X> work = delegate.makeWorkForInput(input);
			return this.doTransaction(work);
		}

		@Override
		public abstract <X> X doTransaction(Work<X> work);

	}

}
