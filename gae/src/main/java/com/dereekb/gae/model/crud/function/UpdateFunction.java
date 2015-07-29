package com.dereekb.gae.model.crud.function;

import com.dereekb.gae.model.crud.function.delegate.UpdateFunctionDelegate;
import com.dereekb.gae.model.crud.function.exception.AtomicFunctionException;
import com.dereekb.gae.model.crud.function.exception.AttributeFailureException;
import com.dereekb.gae.model.crud.pairs.UpdatePair;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Filtered function for updating objects using a template of the same type.
 *
 * @author dereekb
 *
 * @param <T>
 *            Updated type
 */
@Deprecated
public class UpdateFunction<T extends UniqueModel> extends AtomicFunction<T, UpdatePair<T>> {

	private UpdateFunctionDelegate<T> updateDelegate;

	public UpdateFunction() {
		super(false);
	}

	public UpdateFunction(UpdateFunctionDelegate<T> updateDelegate) {
		this();
		this.updateDelegate = updateDelegate;
	}

	public UpdateFunctionDelegate<T> getUpdateDelegate() {
		return this.updateDelegate;
	}

	public void setUpdateDelegate(UpdateFunctionDelegate<T> updateDelegate) {
		this.updateDelegate = updateDelegate;
	}

	@Override
	protected void usePair(UpdatePair<T> pair) throws AtomicFunctionException {
		T template = pair.getTemplate();
		T target = pair.getTarget();

		try {
			this.updateDelegate.update(template, target);
			pair.setSuccessful(true);
		} catch (AttributeFailureException e) {
			pair.setFailureException(e);
			throw new AtomicFunctionException(template, e);
		}
	}

}
