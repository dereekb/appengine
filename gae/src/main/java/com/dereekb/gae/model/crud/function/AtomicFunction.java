package com.dereekb.gae.model.crud.function;

import com.dereekb.gae.model.crud.function.exception.AtomicFunctionException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.function.staged.filter.FilteredStagedFunction;

/**
 * Abstract base used for functions that have a simple function that is iterated
 * over all elements.
 *
 * If any element fails, an exception is raised.
 *
 * @author dereekb
 *
 * @param <T>
 * @param <W>
 */
public abstract class AtomicFunction<T, W extends StagedFunctionObject<T>> extends FilteredStagedFunction<T, W> {

	protected boolean atomic;

	public AtomicFunction() {
		this(true);
	}

	public AtomicFunction(boolean atomic) {
		this.atomic = atomic;
	}

	public boolean isAtomic() {
		return this.atomic;
	}

	public void setAtomic(boolean atomic) {
		this.atomic = atomic;
	}

	@Override
	protected void doFunction() {
		Iterable<W> pairs = this.getWorkingObjects();
		this.usePairs(pairs);
	}

	protected void usePairs(Iterable<W> pairs) {
		for (W pair : pairs) {

			try {
				this.usePair(pair);
			} catch (AtomicFunctionException e) {
				if (this.atomic) {
					RuntimeException cause = e.getCause();
					UniqueModel model = e.getModel();
					throw new AtomicOperationException(model, cause);
				}
			}
		}
	}

	protected abstract void usePair(W pair) throws AtomicFunctionException;

}
