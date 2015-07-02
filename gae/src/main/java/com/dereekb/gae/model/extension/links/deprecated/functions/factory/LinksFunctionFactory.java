package com.dereekb.gae.model.extension.links.deprecated.functions.factory;

import com.dereekb.gae.model.extension.links.deprecated.functions.LinksFunction;
import com.dereekb.gae.model.extension.links.deprecated.functions.LinksHandler;
import com.dereekb.gae.model.extension.links.deprecated.functions.LinksPair;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.factory.AbstractFilteredStagedFunctionFactory;
import com.dereekb.gae.utilities.function.staged.filter.FallableObjectFilter;
import com.dereekb.gae.utilities.function.staged.filter.StagedFunctionFiltersMap;

/**
 * Factory for creating {@link LinksFunction} instances.
 *
 * To ensure safety when unlinking, objects are saved synchronously.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class LinksFunctionFactory<T extends UniqueModel> extends AbstractFilteredStagedFunctionFactory<LinksFunction<T>, T, LinksPair<T>> {

	private LinksHandler<T> handler = null;

	public LinksFunctionFactory() {
		super();
	}

	@Override
    protected void addSpecialFilters(StagedFunctionFiltersMap<T, LinksPair<T>> filtersMap) {
		super.addSpecialFilters(filtersMap);
		FallableObjectFilter<T, LinksPair<T>> filter = new FallableObjectFilter<T, LinksPair<T>>();
		filtersMap.add(StagedFunctionStage.PRE_SAVING, filter);
	}

	@Override
	protected LinksFunction<T> newStagedFunction() {
		LinksFunction<T> linksFunction = new LinksFunction<T>();

		if (this.handler != null) {
			linksFunction.setHandler(this.handler);
		}

		return linksFunction;
	}

	public LinksHandler<T> getHandler() {
		return this.handler;
	}

	public void setHandler(LinksHandler<T> handler) {
		this.handler = handler;
	}

}
