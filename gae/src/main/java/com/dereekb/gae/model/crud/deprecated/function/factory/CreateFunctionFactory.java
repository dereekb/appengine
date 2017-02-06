package com.dereekb.gae.model.crud.deprecated.function.factory;

import com.dereekb.gae.model.crud.deprecated.function.CreateFunction;
import com.dereekb.gae.model.crud.deprecated.function.delegate.CreateFunctionDelegate;
import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.function.staged.factory.AbstractFilteredStagedFunctionFactory;

/**
 * Factory for creating a new CreateFunction.
 *
 * CreateFunctions generated do not have their createDelegates set.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model Type
 * @param <K>
 *            Key Type
 */
@Deprecated
public class CreateFunctionFactory<T extends UniqueModel> extends AbstractFilteredStagedFunctionFactory<CreateFunction<T>, T, CreatePair<T>> {

	private CreateFunctionDelegate<T> delegate;

	public CreateFunctionFactory() {}

	public CreateFunctionFactory(CreateFunctionDelegate<T> delegate) {
		this.delegate = delegate;
	}

	public CreateFunctionDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(CreateFunctionDelegate<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	protected CreateFunction<T> newStagedFunction() {
		CreateFunction<T> function = new CreateFunction<T>(this.delegate);
		return function;
	}

}
