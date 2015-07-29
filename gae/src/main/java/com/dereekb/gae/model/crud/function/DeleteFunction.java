package com.dereekb.gae.model.crud.function;

import java.util.Collection;

import com.dereekb.gae.model.crud.function.delegate.DeleteFunctionDelegate;
import com.dereekb.gae.model.crud.function.exception.CancelDeleteException;
import com.dereekb.gae.model.crud.pairs.DeletePair;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.utilities.collections.pairs.SuccessResultsPair;
import com.dereekb.gae.utilities.function.staged.filter.FilteredStagedFunction;

/**
 * Function for deleting objects. The {@link DeleteFunctionDelegate} handles
 * preparing the object for deletion, while a
 * {@link SetterFunctionDeleteDelegate} should be used for actually deleting the
 * objects if they are to be deleted during this function's run.
 *
 * The delegate is optional, since some types may not need to be prepared for
 * deletion.
 *
 * @author dereekb
 *
 * @param <T>
 *            Deleted type.
 */
@Deprecated
public class DeleteFunction<T> extends FilteredStagedFunction<T, DeletePair<T>> {

	private DeleteFunctionDelegate<T> deleteDelegate;

	public DeleteFunction() {
		super();
	}

	public DeleteFunction(DeleteFunctionDelegate<T> deleteDelegate) {
		this.deleteDelegate = deleteDelegate;
	}

	@Override
	protected void doFunction() {
		Iterable<DeletePair<T>> pairs = this.getWorkingObjects();
		this.prepareDelete(pairs);
	}

	protected void prepareDelete(Iterable<DeletePair<T>> pairs) {
		Collection<T> objects = DeletePair.getSources(pairs);
		boolean success = true;

		try {
			if (this.deleteDelegate != null) {
				this.deleteDelegate.deleteObjects(objects);
			}
		} catch (CancelDeleteException e) {
			throw new AtomicOperationException(e.getRejected(), e);
		}

		SuccessResultsPair.setResultPairsSuccess(pairs, success);
	}

	public DeleteFunctionDelegate<T> getDeleteDelegate() {
		return this.deleteDelegate;
	}

	public void setDeleteDelegate(DeleteFunctionDelegate<T> deleteDelegate) {
		if (deleteDelegate == null) {
			throw new NullPointerException();
		}

		this.deleteDelegate = deleteDelegate;
	}

}
