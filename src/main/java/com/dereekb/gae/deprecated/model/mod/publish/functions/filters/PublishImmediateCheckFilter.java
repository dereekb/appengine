package com.thevisitcompany.gae.deprecated.model.mod.publish.functions.filters;

import com.thevisitcompany.gae.deprecated.model.mod.publish.KeyedPublishableModel;
import com.thevisitcompany.gae.deprecated.model.mod.publish.functions.PublishPair;
import com.thevisitcompany.gae.utilities.filters.FilterResult;
import com.thevisitcompany.gae.utilities.filters.FilterResults;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionStage;
import com.thevisitcompany.gae.utilities.function.staged.filter.StagedFunctionObjectFilter;

public class PublishImmediateCheckFilter<T extends KeyedPublishableModel<Long>>
        implements StagedFunctionObjectFilter<T, PublishPair<T>> {

	public AllowImmediatePublishActionDelegate<T> delegate;

	public PublishImmediateCheckFilter(AllowImmediatePublishActionDelegate<T> delegate) {
		if (delegate == null) {
			throw new NullPointerException("Delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	@Override
	public FilterResults<PublishPair<T>> filterFunctionObjects(StagedFunctionStage stage,
	                                                           Iterable<PublishPair<T>> pairs) {

		FilterResults<PublishPair<T>> results = new FilterResults<PublishPair<T>>();

		for (PublishPair<T> pair : pairs) {
			if (pair.doImmediately()) {
				T object = pair.getSource();
				boolean canDoImmediate = this.delegate.canDoActionImmediately(pair.getAction(), object);
				results.add(FilterResult.withBoolean(canDoImmediate), pair);
			} else {
				results.add(FilterResult.PASS, pair);
			}
		}

		return results;
	}

	public AllowImmediatePublishActionDelegate<T> getDelegate() {
		return delegate;
	}

	public void setDelegate(AllowImmediatePublishActionDelegate<T> delegate) {
		this.delegate = delegate;
	}
}
