package com.dereekb.gae.utilities.function.staged.factory;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.deprecated.function.staged.factory.filter.StagedFunctionFilterFactory;
import com.dereekb.gae.utilities.deprecated.function.staged.factory.filter.StagedFunctionFilterMapFactory;
import com.dereekb.gae.utilities.deprecated.function.staged.factory.filter.StagedFunctionObjectFilterFactory;
import com.dereekb.gae.utilities.deprecated.function.staged.factory.pairs.StagedFunctionStagePair;
import com.dereekb.gae.utilities.deprecated.function.staged.filter.FilteredStagedFunction;
import com.dereekb.gae.utilities.deprecated.function.staged.filter.FilteredStagedFunctionObserver;
import com.dereekb.gae.utilities.deprecated.function.staged.filter.StagedFunctionFilter;
import com.dereekb.gae.utilities.deprecated.function.staged.filter.StagedFunctionFiltersMap;
import com.dereekb.gae.utilities.deprecated.function.staged.filter.StagedFunctionObjectFilter;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * Abstract class that uses a {@link StagedFunctionFilterMapFactory} and set of {@link StagedFunctionFilterFactory} and
 * {@link StagedFunctionObjectFilterFactory} for creating a filters map for created {@link FilteredStagedFunction}.
 * 
 * @author dereekb
 *
 * @param <F> {@link FilteredStagedFunction} that this factory creates.
 * @param <T> Type of the base object used in this function.
 * @param <W> Functional Wrapper of the main object that extends StagedFunctionObject.
 */
public abstract class AbstractFilteredStagedFunctionFactory<F extends FilteredStagedFunction<T, W>, T, W extends StagedFunctionObject<T>> extends AbstractStagedFunctionFactory<F, T, W>
        implements StagedFunctionFilterMapFactory<T, W> {

	private StagedFunctionFilterMapFactory<T, W> filtersMapFactory = this;
	private List<StagedFunctionFilterFactory<StagedFunctionFilter<T>, T>> filterFactories = new ArrayList<StagedFunctionFilterFactory<StagedFunctionFilter<T>, T>>();
	private List<StagedFunctionObjectFilterFactory<StagedFunctionObjectFilter<T, W>, T, W>> objectFilterFactories = new ArrayList<StagedFunctionObjectFilterFactory<StagedFunctionObjectFilter<T, W>, T, W>>();

	// Optional Observer
	private FilteredStagedFunctionObserver<T, W> filtersObserverSingleton;
	private Factory<FilteredStagedFunctionObserver<T, W>> filtersObserverFactory;

	@Override
	protected F makeStagedFunction() {
		F functionHandler = super.makeStagedFunction();
		this.generateAndApplyAllFilters(functionHandler);
		this.applyFiltersObserver(functionHandler);
		return functionHandler;
	}

	public void generateAndApplyAllFilters(F functionHandler) {
		StagedFunctionFiltersMap<T, W> filtersMap = this.generateFiltersMap();
		this.applyAllFilters(filtersMap);
		functionHandler.setFilterMap(filtersMap);
	}

	public void applyFiltersObserver(F functionHandler) {
		if (this.filtersObserverFactory != null) {
			FilteredStagedFunctionObserver<T, W> observer = this.filtersObserverFactory.make();
			functionHandler.setFilterObserver(observer);
		} else if (this.filtersObserverSingleton != null) {
			functionHandler.setFilterObserver(filtersObserverSingleton);
		}
	}

	public void applyAllFilters(F functionHandler) {
		this.applyAllFilters(functionHandler.getFiltersMap());
	}

	public void applyAllFilters(StagedFunctionFiltersMap<T, W> filterMap) {

		for (StagedFunctionFilterFactory<StagedFunctionFilter<T>, T> filterFactory : this.filterFactories) {
			StagedFunctionStagePair<StagedFunctionFilter<T>> pair = filterFactory.makeFilter();
			filterMap.add(pair.getStages(), pair.getObject());
		}

		for (StagedFunctionObjectFilterFactory<StagedFunctionObjectFilter<T, W>, T, W> filterFactory : this.objectFilterFactories)
		{
			StagedFunctionStagePair<StagedFunctionObjectFilter<T, W>> pair = filterFactory.makeObjectFilter();
			filterMap.add(pair.getStages(), pair.getObject());
		}

	}

	protected final StagedFunctionFiltersMap<T, W> generateFiltersMap() {
		StagedFunctionFiltersMap<T, W> filtersMap = this.filtersMapFactory.makefiltersMap();
		this.addSpecialFilters(filtersMap);
		return filtersMap;
	}

	@Override
	public StagedFunctionFiltersMap<T, W> makefiltersMap() {
		StagedFunctionFiltersMap<T, W> filtersMap = new StagedFunctionFiltersMap<T, W>();
		return filtersMap;
	}

	/**
	 * Adds any special filters that should always go into the map for the generated.
	 * 
	 * They are added to the filters map when generated, and as a result are called before any other filters.
	 */
	protected void addSpecialFilters(StagedFunctionFiltersMap<T, W> filtersMap) {}

	public StagedFunctionFilterMapFactory<T, W> getFiltersMapFactory() {
		return filtersMapFactory;
	}

	public void setFiltersMapFactory(StagedFunctionFilterMapFactory<T, W> filtersMapFactory) {
		this.filtersMapFactory = filtersMapFactory;
	}

	public List<StagedFunctionFilterFactory<StagedFunctionFilter<T>, T>> getFilterFactories() {
		return filterFactories;
	}

	public void setFilterFactories(List<StagedFunctionFilterFactory<StagedFunctionFilter<T>, T>> filterFactories) {
		this.filterFactories = filterFactories;
	}

	public List<StagedFunctionObjectFilterFactory<StagedFunctionObjectFilter<T, W>, T, W>> getObjectFilterFactories() {
		return objectFilterFactories;
	}

	public void setObjectFilterFactories(List<StagedFunctionObjectFilterFactory<StagedFunctionObjectFilter<T, W>, T, W>> objectFilterFactories) {
		this.objectFilterFactories = objectFilterFactories;
	}

	public FilteredStagedFunctionObserver<T, W> getFiltersObserverSingleton() {
		return filtersObserverSingleton;
	}

	public void setFiltersObserverSingleton(FilteredStagedFunctionObserver<T, W> filtersObserverSingleton) {
		this.filtersObserverSingleton = filtersObserverSingleton;
	}

	public Factory<FilteredStagedFunctionObserver<T, W>> getFiltersObserverFactory() {
		return filtersObserverFactory;
	}

	public void setFiltersObserverFactory(Factory<FilteredStagedFunctionObserver<T, W>> filtersObserverFactory) {
		this.filtersObserverFactory = filtersObserverFactory;
	}

}
