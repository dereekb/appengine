package com.dereekb.gae.model.extension.links.deprecated.functions.filters;

import java.util.Collection;

import com.dereekb.gae.model.extension.links.deprecated.functions.LinksPair;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.function.staged.filter.AbstractStagedFunctionObjectFilter;

/**
 * Filter for checking that an item is not attempting to link to itself.
 *
 * @author dereekb
 *
 * @param <T>
 * @param <ModelModelKeyey>
 */
public class CannotLinkSelfFilter<T extends UniqueModel> extends AbstractStagedFunctionObjectFilter<T, LinksPair<T>> {

	@Override
	public FilterResult filterObject(LinksPair<T> pair) {
		T object = pair.getTarget();
		ModelKey key = object.getModelKey();

		boolean containsSelf = false;

		if (key != null) {
			Collection<ModelKey> links = pair.getLinks();
			containsSelf = links.contains(key);
		}

		return FilterResult.withBoolean(containsSelf == false);
	}

}
