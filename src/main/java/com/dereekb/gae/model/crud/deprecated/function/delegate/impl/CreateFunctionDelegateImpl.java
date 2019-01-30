package com.dereekb.gae.model.crud.deprecated.function.delegate.impl;

import com.dereekb.gae.model.crud.deprecated.function.delegate.CreateFunctionDelegate;
import com.dereekb.gae.model.crud.deprecated.function.delegate.UpdateFunctionDelegate;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;
import com.dereekb.gae.model.crud.exception.InvalidTemplateException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * {@link CreateFunctionDelegate} implementation that uses a
 * {@link UpdateFunctionDelegate} to update the target template.
 *
 * @author dereekb
 *
 */
@Deprecated
public class CreateFunctionDelegateImpl<T extends UniqueModel>
        implements CreateFunctionDelegate<T> {

	/**
	 * Generates the new model.
	 */
	private Factory<T> modelFactory;

	/**
	 * Used to apply values to the new model.
	 */
	private UpdateFunctionDelegate<T> updateDelegate;

	public CreateFunctionDelegateImpl(Factory<T> modelFactory, UpdateFunctionDelegate<T> updateDelegate) {
		this.modelFactory = modelFactory;
		this.updateDelegate = updateDelegate;
	}

	@Override
	public T create(T source) throws InvalidTemplateException {
		T newModel = this.modelFactory.make();

		try {
			this.updateDelegate.update(source, newModel);
		} catch (InvalidAttributeException e) {
			throw new InvalidTemplateException(e);
		}

		return newModel;
	}

	public Factory<T> getModelFactory() {
		return this.modelFactory;
	}

	public void setModelFactory(Factory<T> modelFactory) {
		this.modelFactory = modelFactory;
	}

	public UpdateFunctionDelegate<T> getUpdateDelegate() {
		return this.updateDelegate;
	}

	public void setUpdateDelegate(UpdateFunctionDelegate<T> updateDelegate) {
		this.updateDelegate = updateDelegate;
	}

}
