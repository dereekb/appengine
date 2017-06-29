package com.dereekb.gae.model.extension.links.task;

import com.dereekb.gae.model.extension.links.components.system.LinkSystem;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link LinkModelChangeTaskFactory} that performs the changes within
 * transactions for safety.
 * <p>
 * This is only recommended for unlinking where a single model is ultimately
 * being saved after modification.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class LinkModelTransactionChangeTaskFactory<T extends UniqueModel> extends LinkModelChangeTaskFactory<T> {

	public LinkModelTransactionChangeTaskFactory(LinkSystem system, LinkModelChangeTaskFactoryDelegate delegate) {
		super(system, delegate);
	}

	protected class TransactionSafeLinkModelChangeTask extends LinkModelChangeTask {

		public TransactionSafeLinkModelChangeTask(String modelType,
		        com.dereekb.gae.model.extension.links.task.LinkModelChangeTaskFactory.ModelLinkChangeTaskDelegate delegate) {
			super(modelType, delegate);
		}

		// MARK: Task
		@Override
		public void doTask(ModelKeyListAccessor<T> input) throws FailedTaskException {

			/*
			 * May not get implemented either due to amount of overhead
			 * involved.
			 */
			throw new UnsupportedOperationException("No implemented.");
		}

	}

}
