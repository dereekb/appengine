package com.dereekb.gae.model.crud.function.factory;

import com.dereekb.gae.model.crud.function.DeleteFunction;
import com.dereekb.gae.model.crud.function.delegate.DeleteFunctionDelegate;
import com.dereekb.gae.model.crud.function.pairs.DeletePair;
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
public class DeleteFunctionFactory<T> extends AbstractFilteredStagedFunctionFactory<DeleteFunction<T>, T, DeletePair<T>> {

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
