package com.dereekb.gae.model.crud.extension.delete;

import java.util.List;

import com.dereekb.gae.model.crud.function.delegate.DeleteFunctionDelegate;
import com.dereekb.gae.model.crud.function.exception.CancelDeleteException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResults;

/**
 * Acts as a wrapper for a {@link DeleteFunctionDelegate}. Checks whether or not each of the elements can be deleted or not, before calling the wrapped delegate.
 * @author dereekb
 *
 * @param <T>
 */
public class FilteredDeleteFunctionDelegate<T extends UniqueModel>
        implements DeleteFunctionDelegate<T> {

	private Filter<T> canDeleteFilter;
	private DeleteFunctionDelegate<T> deleteDelegate;

	public FilteredDeleteFunctionDelegate() {}

	public FilteredDeleteFunctionDelegate(Filter<T> canDeleteFilter, DeleteFunctionDelegate<T> deleteDelegate) {
		this.canDeleteFilter = canDeleteFilter;
		this.deleteDelegate = deleteDelegate;
	}

	public Filter<T> getCanDeleteFilter() {
		return this.canDeleteFilter;
	}

	public void setCanDeleteFilter(Filter<T> canDeleteFilter) {
		this.canDeleteFilter = canDeleteFilter;
	}

	public DeleteFunctionDelegate<T> getDeleteDelegate() {
		return this.deleteDelegate;
	}

	public void setDeleteDelegate(DeleteFunctionDelegate<T> deleteDelegate) {
		this.deleteDelegate = deleteDelegate;
	}

	@Override
	public void deleteObjects(Iterable<T> objects) throws CancelDeleteException {
		FilterResults<T> results = this.canDeleteFilter.filterObjects(objects);
		List<T> failed = results.getFailingObjects();

		if (failed.isEmpty()) {
			this.deleteDelegate.deleteObjects(objects);
		} else {
			throw new CancelDeleteException(failed);
		}

	}

}
