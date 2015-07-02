package com.dereekb.gae.model.crud.function.factory;

import com.dereekb.gae.model.crud.function.UpdateFunction;
import com.dereekb.gae.model.crud.function.delegate.UpdateFunctionDelegate;
import com.dereekb.gae.model.crud.function.pairs.UpdatePair;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.function.staged.factory.AbstractFilteredStagedFunctionFactory;

/**
 * Factory for creating configured {@link UpdateFunction} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model Type
 * @param <K>
 *            Key Type
 */
public class UpdateFunctionFactory<T extends UniqueModel> extends AbstractFilteredStagedFunctionFactory<UpdateFunction<T>, T, UpdatePair<T>> {

	private UpdateFunctionDelegate<T> delegate;

	public UpdateFunctionFactory() {}

	public UpdateFunctionFactory(UpdateFunctionDelegate<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	protected UpdateFunction<T> newStagedFunction() {
		UpdateFunction<T> function = new UpdateFunction<T>(this.delegate);
		return function;
	}

	public UpdateFunctionDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(UpdateFunctionDelegate<T> delegate) {
		this.delegate = delegate;
	}

}
