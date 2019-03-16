package com.dereekb.gae.model.extension.links.deprecated.functions.filters;

import com.dereekb.gae.model.extension.links.deprecated.functions.LinksAction;
import com.dereekb.gae.model.extension.links.deprecated.functions.LinksPair;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.function.staged.filter.AbstractStagedFunctionObjectFilter;

public class CanLinkFilter<T extends UniqueModel> extends AbstractStagedFunctionObjectFilter<T, LinksPair<T>> {

	private CanLinkFilterDelegate<T> delegate;

	@Override
	public FilterResult filterObject(LinksPair<T> pair) {

		T object = pair.getTarget();
		LinksAction action = pair.getOperation();
		String link = pair.getName();

		return FilterResult.withBoolean(this.delegate.canLink(object, link, action));
	}

	public CanLinkFilterDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(CanLinkFilterDelegate<T> delegate) {
		this.delegate = delegate;
	}

}
