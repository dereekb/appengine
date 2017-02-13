package com.dereekb.gae.model.crud.task.impl.delegate.impl;

import com.dereekb.gae.model.crud.exception.InvalidTemplateException;
import com.dereekb.gae.model.crud.task.impl.delegate.CreateTaskDelegate;
import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.web.api.util.attribute.exception.AttributeUpdateFailureException;

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

	public CreateTaskDelegateImpl() {}

	public CreateTaskDelegateImpl(Factory<T> factory, UpdateTaskDelegate<T> updateDelegate) {
		this.factory = factory;
		this.updateDelegate = updateDelegate;
	}

	public Factory<T> getFactory() {
		return this.factory;
	}

	public void setFactory(Factory<T> factory) {
		this.factory = factory;
	}

	public UpdateTaskDelegate<T> getUpdateDelegate() {
		return this.updateDelegate;
	}

	public void setUpdateDelegate(UpdateTaskDelegate<T> updateDelegate) {
		this.updateDelegate = updateDelegate;
	}

	// MARK: CreateTaskDelegate
	@Override
	public T createFromSource(T source) throws InvalidTemplateException {
		T created = this.factory.make();

		try {
			this.updateDelegate.updateTarget(created, source);
		} catch (AttributeUpdateFailureException e) {
			throw new InvalidTemplateException(e);
		}

		return created;
	}

	@Override
	public String toString() {
		return "CreateTaskDelegateImpl [factory=" + this.factory + ", updateDelegate=" + this.updateDelegate + "]";
	}

}
