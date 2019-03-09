package com.dereekb.gae.model.crud.deprecated.function.factory;

import com.dereekb.gae.model.crud.deprecated.function.DeleteFunction;
import com.dereekb.gae.model.crud.deprecated.function.delegate.DeleteFunctionDelegate;
import com.dereekb.gae.model.crud.pairs.DeletePair;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.function.staged.factory.AbstractFilteredStagedFunctionFactory;

/**
 * Factory for creating a new DeleteFunction.
 *
 * DeleteFunctions generated do not have their createDelegates set.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model Type
 * @param <K>
 *            Key Type
 */
@Deprecated
public class DeleteFunctionFactory<T extends UniqueModel> extends AbstractFilteredStagedFunctionFactory<DeleteFunction<T>, T, DeletePair<T>> {

	private DeleteFunctionDelegate<T> delegate = null;

	public DeleteFunctionFactory() {}

	public DeleteFunctionFactory(DeleteFunctionDelegate<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	protected DeleteFunction<T> newStagedFunction() {
		DeleteFunction<T> function = new DeleteFunction<T>(this.delegate);
		return function;
	}

	public DeleteFunctionDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(DeleteFunctionDelegate<T> delegate) {
		this.delegate = delegate;
	}

}
