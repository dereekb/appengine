package com.dereekb.gae.model.crud.task.impl;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.task.config.AtomicTaskConfig;
import com.dereekb.gae.server.datastore.objectify.helpers.ObjectifyTransactionUtility;
import com.dereekb.gae.server.datastore.objectify.helpers.PartitionDelegate;
import com.dereekb.gae.server.datastore.objectify.helpers.impl.RunnablePartitionDelegateImpl;

/**
 * {@link AtomicTaskImpl} extension that performs
 * {@link #usePairs(Iterable, AtomicTaskConfig)} within a Google App Engine
 * transaction.
 *
 * @author dereekb
 *
 * @param <P>
 *            pairs type
 * @param <C>
 *            configuration type
 */
public abstract class TransactionAtomicTaskImpl<P, C extends AtomicTaskConfig> extends AtomicTaskImpl<P, C> {

	public TransactionAtomicTaskImpl(C defaultConfig) {
		super(defaultConfig);
	}

	@Override
	protected void usePairs(Iterable<P> input,
	                        final C config)
	        throws AtomicOperationException {

		final PartitionDelegate<P, Void> TRANS_DELEGATE = new RunnablePartitionDelegateImpl<P>() {

			@Override
			public void run(final Iterable<P> input) {
				TransactionAtomicTaskImpl.super.usePairs(input, config);
			}

		};

		ObjectifyTransactionUtility.transact().doTransaction(input, TRANS_DELEGATE);
	}

}
