package com.dereekb.gae.model.extension.search.document.index.filter;

import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.deprecated.function.staged.filter.StagedFunctionFilter;
import com.dereekb.gae.utilities.deprecated.function.staged.filter.StagedFunctionFilterDelegate;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.FilterResults;

/**
 * Used to filter elements to see if they can be indexed or not.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class CanIndexDocumentFilter<T>
        implements StagedFunctionFilter<T> {

	private CanIndexDocumentFilterDelegate<T> delegate;

	public CanIndexDocumentFilter(CanIndexDocumentFilterDelegate<T> delegate) {
		this.delegate = delegate;
	}

	public CanIndexDocumentFilterDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(CanIndexDocumentFilterDelegate<T> delegate) {
		this.delegate = delegate;
	}

	// StagedFunctionFilter
	@Override
	public <W> FilterResults<W> filterObjectsWithDelegate(StagedFunctionStage stage,
	                                                      Iterable<W> sources,
	                                                      StagedFunctionFilterDelegate<T, W> delegate) {
		FilterResults<W> results = new FilterResults<W>();

		for (W source : sources) {
			T model = delegate.getModel(source, stage);
			boolean canIndex = this.delegate.canIndex(model);

			FilterResult result = FilterResult.withBoolean(canIndex);
			results.add(result, source);
		}

		return results;

	}

}
