package com.dereekb.gae.model.crud.task.impl.delegate.impl;

import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link UpdateTaskDelegate} that performs an update before passing on the
 * request to another {@link UpdateTaskDelegate}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractChainedUpdateTaskDelegate<T>
        implements UpdateTaskDelegate<T> {

	private UpdateTaskDelegate<T> updateTaskDelegate;

	public AbstractChainedUpdateTaskDelegate(UpdateTaskDelegate<T> updateTaskDelegate) {
		this.setUpdateTaskDelegate(updateTaskDelegate);
	}

	public UpdateTaskDelegate<T> getUpdateTaskDelegate() {
		return this.updateTaskDelegate;
	}

	public void setUpdateTaskDelegate(UpdateTaskDelegate<T> updateTaskDelegate) {
		if (updateTaskDelegate == null) {
			throw new IllegalArgumentException("updateTaskDelegate cannot be null.");
		}

		this.updateTaskDelegate = updateTaskDelegate;
	}

	// MARK: UpdateTaskDelegate
	@Override
	public void updateTarget(T target,
	                         T template)
	        throws InvalidAttributeException {
		this.chainUpdateTarget(target, template);
		this.updateTaskDelegate.updateTarget(target, template);
	}

	protected abstract void chainUpdateTarget(T target,
	                                          T template)
	        throws InvalidAttributeException;

}
