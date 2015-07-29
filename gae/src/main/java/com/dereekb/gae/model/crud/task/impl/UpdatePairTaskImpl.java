package com.dereekb.gae.model.crud.task.impl;

import com.dereekb.gae.model.crud.function.exception.AtomicFunctionException;
import com.dereekb.gae.model.crud.function.exception.AttributeFailureException;
import com.dereekb.gae.model.crud.pairs.UpdatePair;
import com.dereekb.gae.model.crud.task.UpdatePairTask;
import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link Task} for updating a model given another template model.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class UpdatePairTaskImpl<T extends UniqueModel> extends AtomicPairTask<UpdatePair<T>>
        implements UpdatePairTask<T> {

	private UpdateTaskDelegate<T> delegate;

	public UpdateTaskDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(UpdateTaskDelegate<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void usePair(UpdatePair<T> pair) {
		T template = pair.getTemplate();
		T target = pair.getTarget();

		try {
			this.delegate.updateTarget(target, template);
			pair.setSuccessful(true);
		} catch (AttributeFailureException e) {
			pair.setFailureException(e);
			throw new AtomicFunctionException(template, e);
		}
	}

	@Override
	public String toString() {
		return "UpdatePairTaskImpl [delegate=" + this.delegate + ", atomicTask=" + this.atomicTask + "]";
	}

}
