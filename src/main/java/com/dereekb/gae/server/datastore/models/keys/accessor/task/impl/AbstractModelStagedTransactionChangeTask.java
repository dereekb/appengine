package com.dereekb.gae.server.datastore.models.keys.accessor.task.impl;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.objectify.helpers.CleanupPartitionDelegate;
import com.dereekb.gae.server.datastore.objectify.helpers.ObjectifyTransactionUtility;
import com.dereekb.gae.server.datastore.utility.StagedTransactionChange;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.googlecode.objectify.Work;

/**
 * Abstract {@link AbstractModelKeyListAccessorTask} that performs changes to the  
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractModelStagedTransactionChangeTask<T extends UniqueModel> extends AbstractModelKeyListAccessorTask<T> {

	private final Integer partitionSize;

	public AbstractModelStagedTransactionChangeTask(Integer partitionSize) {
		super();

		if (partitionSize == null) {
			partitionSize = ObjectifyTransactionUtility.MAX_TRANSACTION_ELEMENTS;
		}

		this.partitionSize = partitionSize;
	}

	// MARK: Task
	@Override
	public void doTask(ModelKeyListAccessor<T> input) throws FailedTaskException {
		ObjectifyTransactionUtility.transact().doTransactionWithPartition(input,
		        new CleanupPartitionDelegate<ModelKey, StagedTransactionChange>() {

			        @Override
			        public Work<StagedTransactionChange> makeWorkForInput(Iterable<ModelKey> input) {
				        return makeWork(input);
			        }

			        @Override
			        public StagedTransactionChange cleanup(StagedTransactionChange output) {
				        // Finish changes as they complete.
				        output.finishChanges();
				        return output;
			        }

		        }, partitionSize);
	}

	protected abstract AbstractTransactionWork makeWork(Iterable<ModelKey> input);

	protected abstract class AbstractTransactionWork
	        implements Work<StagedTransactionChange> {

		protected final Iterable<ModelKey> input;

		public AbstractTransactionWork(Iterable<ModelKey> input) {
			super();
			this.input = input;
		}

	}

}
