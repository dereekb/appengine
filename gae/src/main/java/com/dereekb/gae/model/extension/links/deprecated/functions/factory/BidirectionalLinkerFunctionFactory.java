package com.dereekb.gae.model.extension.links.deprecated.functions.factory;

import com.dereekb.gae.model.extension.links.deprecated.functions.BidirectionalLinkerFunction;
import com.dereekb.gae.model.extension.links.deprecated.functions.BidirectionalLinkerFunctionTypeDelegate;
import com.dereekb.gae.model.extension.links.deprecated.functions.LinksHandler;
import com.dereekb.gae.model.extension.links.deprecated.functions.LinksPair;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.factory.AbstractFilteredStagedFunctionFactory;
import com.dereekb.gae.utilities.function.staged.filter.FallableObjectFilter;
import com.dereekb.gae.utilities.function.staged.filter.StagedFunctionFiltersMap;

/**
 * Factory for creating {@link BidirectionalLinkerFunction} instances.
 *
 * @author dereekb
 *
 * @param <T>
 * @param <S>
 */
public class BidirectionalLinkerFunctionFactory<T extends UniqueModel, S extends UniqueModel, K> extends AbstractFilteredStagedFunctionFactory<BidirectionalLinkerFunction<T, S>, T, LinksPair<T>> {

	private boolean safe = true;

	private LinksHandler<T> primaryHandler;
	private LinksHandler<S> secondaryHandler;

	private BidirectionalLinkerFunctionTypeDelegate typeDelegate;

	private Getter<S> secondaryGetter;
	private ConfiguredSetter<S> secondarySetter;

	@Override
    protected void addSpecialFilters(StagedFunctionFiltersMap<T, LinksPair<T>> filtersMap) {
		super.addSpecialFilters(filtersMap);
		FallableObjectFilter<T, LinksPair<T>> filter = new FallableObjectFilter<T, LinksPair<T>>();
		filtersMap.add(StagedFunctionStage.PRE_SAVING, filter);
	}

	@Override
	protected BidirectionalLinkerFunction<T, S> newStagedFunction() {
		BidirectionalLinkerFunction<T, S> linkerFunction = new BidirectionalLinkerFunction<T, S>();
		linkerFunction.setSafe(this.safe);

		if (this.primaryHandler != null) {
			linkerFunction.setPrimaryHandler(this.primaryHandler);
		}

		if (this.secondaryHandler != null) {
			linkerFunction.setSecondaryHandler(this.secondaryHandler);
		}

		if (this.typeDelegate != null) {
			linkerFunction.setTypeDelegate(this.typeDelegate);
		}

		if (this.secondaryGetter != null) {
			linkerFunction.setSecondaryGetter(this.secondaryGetter);
		}

		if (this.secondarySetter != null) {
			linkerFunction.setSecondarySetter(this.secondarySetter);
		}

		return linkerFunction;
	}

	public LinksHandler<T> getPrimaryHandler() {
		return this.primaryHandler;
	}

	public void setPrimaryHandler(LinksHandler<T> primaryHandler) {
		this.primaryHandler = primaryHandler;
	}

	public LinksHandler<S> getSecondaryHandler() {
		return this.secondaryHandler;
	}

	public void setSecondaryHandler(LinksHandler<S> secondaryHandler) {
		this.secondaryHandler = secondaryHandler;
	}

	public BidirectionalLinkerFunctionTypeDelegate getTypeDelegate() {
		return this.typeDelegate;
	}

	public void setTypeDelegate(BidirectionalLinkerFunctionTypeDelegate typeDelegate) {
		this.typeDelegate = typeDelegate;
	}

	public boolean isSafe() {
		return this.safe;
	}

	public void setSafe(boolean safe) {
		this.safe = safe;
	}

	public Getter<S> getSecondaryGetter() {
		return this.secondaryGetter;
	}

	public void setSecondaryGetter(Getter<S> secondaryGetter) {
		this.secondaryGetter = secondaryGetter;
	}

	public ConfiguredSetter<S> getSecondarySetter() {
		return this.secondarySetter;
	}

	public void setSecondarySetter(ConfiguredSetter<S> secondarySetter) {
		this.secondarySetter = secondarySetter;
	}


}
