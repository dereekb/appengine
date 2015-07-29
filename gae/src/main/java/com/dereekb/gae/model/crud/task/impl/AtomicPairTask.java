package com.dereekb.gae.model.crud.task.impl;

import com.dereekb.gae.model.crud.exception.AtomicFunctionException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * Abstract {@link IterableTask} that is used for performing the same task over
 * multiple elements that can fail with {@link AtomicFunctionException}.
 *
 * @author dereekb
 *
 * @param <P>
 *            pair type
 */
public abstract class AtomicPairTask<P>
        implements IterableTask<P> {

	protected boolean atomicTask;

	public boolean isAtomicTask() {
		return this.atomicTask;
	}

	public void setAtomicTask(boolean atomicTask) {
		this.atomicTask = atomicTask;
	}

	@Override
	public void doTask(Iterable<P> input) throws FailedTaskException {
		for (P pair : input) {
			try {
				this.usePair(pair);
			} catch (AtomicFunctionException e) {
				if (this.atomicTask) {
					RuntimeException cause = e.getCause();
					UniqueModel model = e.getModel();
					throw new AtomicOperationException(model, cause);
				}
			}
		}
	}

	public abstract void usePair(P pair);

}
