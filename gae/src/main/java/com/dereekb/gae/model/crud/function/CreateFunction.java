package com.dereekb.gae.model.crud.function;

import com.dereekb.gae.model.crud.function.delegate.CreateFunctionDelegate;
import com.dereekb.gae.model.crud.function.exception.AtomicFunctionException;
import com.dereekb.gae.model.crud.function.exception.InvalidTemplateException;
import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Filtered Function used for creating objects using a template of the same
 * type.
 *
 * @author dereekb
 *
 * @param <T>
 *            Created type
 */
@Deprecated
public class CreateFunction<T extends UniqueModel> extends AtomicFunction<T, CreatePair<T>> {

	private CreateFunctionDelegate<T> createDelegate;

	public CreateFunction() {
		super(false);
	}

	public CreateFunction(CreateFunctionDelegate<T> delegate) {
		this();
		this.createDelegate = delegate;
	}

	public CreateFunctionDelegate<T> getCreateDelegate() {
		return this.createDelegate;
	}

	public void setCreateDelegate(CreateFunctionDelegate<T> createDelegate) {
		if (createDelegate == null) {
			throw new NullPointerException();
		}

		this.createDelegate = createDelegate;
	}

	@Override
	protected void usePair(CreatePair<T> pair) throws AtomicFunctionException {
		T source = pair.getSource();

		try {
			T result = this.createDelegate.create(source);
			pair.setResult(result);
		} catch (InvalidTemplateException e) {
			pair.flagFailure();
			throw new AtomicFunctionException(source, e);
		}
	}

}
