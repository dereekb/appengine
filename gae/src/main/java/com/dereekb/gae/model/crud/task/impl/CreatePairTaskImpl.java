package com.dereekb.gae.model.crud.task.impl;

import com.dereekb.gae.model.crud.exception.AtomicFunctionException;
import com.dereekb.gae.model.crud.exception.InvalidTemplateException;
import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.model.crud.task.CreatePairTask;
import com.dereekb.gae.model.crud.task.impl.delegate.CreateTaskDelegate;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link Task} for creating a new model, given another template model.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class CreatePairTaskImpl<T extends UniqueModel> extends AtomicPairTask<CreatePair<T>>
        implements CreatePairTask<T> {

	private CreateTaskDelegate<T> delegate;

	public CreatePairTaskImpl(CreateTaskDelegate<T> delegate) {
		this.delegate = delegate;
	}

	public CreateTaskDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(CreateTaskDelegate<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void usePair(CreatePair<T> pair) {
		T source = pair.getSource();

		try {
			T result = this.delegate.createFromSource(source);
			pair.setResult(result);
		} catch (InvalidTemplateException e) {
			pair.flagFailure();
			throw new AtomicFunctionException(source, e);
		}
	}

	@Override
	public String toString() {
		return "CreatePairTaskImpl [delegate=" + this.delegate + ", atomicTask=" + this.atomicTask + "]";
	}

}
