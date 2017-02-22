package com.dereekb.gae.model.crud.task.impl.delegate.impl;

import com.dereekb.gae.model.crud.task.impl.delegate.CreateTaskDelegate;
import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link CreateTaskDelegate} implementation that uses a {@link Factory} and a
 * {@link UpdateTaskDelegate}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class CreateTaskDelegateImpl<T>
        implements CreateTaskDelegate<T> {

	private Factory<T> factory;
	private UpdateTaskDelegate<T> updateDelegate;

	public CreateTaskDelegateImpl(Factory<T> factory, UpdateTaskDelegate<T> updateDelegate)
	        throws IllegalArgumentException {
		this.setFactory(factory);
		this.setUpdateDelegate(updateDelegate);
	}

	public Factory<T> getFactory() {
		return this.factory;
	}

	public void setFactory(Factory<T> factory) throws IllegalArgumentException {
		if (factory == null) {
			throw new IllegalArgumentException("Factory cannot be null.");
		}

		this.factory = factory;
	}

	public UpdateTaskDelegate<T> getUpdateDelegate() {
		return this.updateDelegate;
	}

	public void setUpdateDelegate(UpdateTaskDelegate<T> updateDelegate) throws IllegalArgumentException {
		if (updateDelegate == null) {
			throw new IllegalArgumentException("UpdateDelegate cannot be null.");
		}

		this.updateDelegate = updateDelegate;
	}

	// MARK: CreateTaskDelegate
	@Override
	public T createFromSource(T source) throws InvalidAttributeException {
		T created = this.factory.make();
		this.updateDelegate.updateTarget(created, source);
		return created;
	}

	@Override
	public String toString() {
		return "CreateTaskDelegateImpl [factory=" + this.factory + ", updateDelegate=" + this.updateDelegate + "]";
	}

}
