package com.dereekb.gae.utilities.function.staged.filter;

import java.util.List;

import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStageMap;
import com.dereekb.gae.utilities.function.staged.observer.StagedFunctionObserverMap;

/**
 * Filters map used by a {@link FilteredStagedFunction} for notifying listening filters.
 * 
 * Allows two types of filters, {@link StagedFunctionFilter} which have access only to the models being worked on, and
 * {@link StagedFunctionObjectFilter} which have access to the set of {@link StagedFunctionObject}.
 * 
 * The set of {@link StagedFunctionObjectFilter} are run first, followed by the set of {@link StagedFunctionFilter} on the input values.
 * 
 * @author dereekb
 *
 * @param <T>
 *            Type of the base object used in this function.
 * @param <W>
 *            Functional Wrapper of the main object that extends StagedFunctionObject.
 * @see {@link StagedFunctionObserverMap} for modifying objects instead of filtering them.
 */
public class StagedFunctionFiltersMap<T, W extends StagedFunctionObject<T>> extends StagedFunctionStageMap<StagedFunctionFilter<T>, StagedFunctionObjectFilter<T, W>>
        implements StagedFunctionFilterDelegate<T, W> {

	public StagedFunctionFiltersMap() {};

	/**
	 * Prepares the filters for being used.
	 */
	public void prepareFilters() {}

	public void clearNormalFilters() {
		this.clearNormal();
	}

	public void clearObjectFilters() {
		this.clearObjectDepdenents();
	}

	public void clearAllFilters() {
		this.clearNormalFilters();
		this.clearObjectFilters();
	}

	/**
	 * Filters using the 'normal' filters only.
	 * 
	 * @param stage
	 * @param sources
	 * @return
	 */
	public final FilterResults<W> filterNormal(StagedFunctionStage stage,
	                                           Iterable<W> sources) {
		FilterResults<W> results = null;
		Iterable<W> passingItems = sources;
		List<StagedFunctionFilter<T>> filtersList = this.normal.valuesForKey(stage);

		if (filtersList.isEmpty()) {
			results = new FilterResults<W>(sources);
		} else {
			for (StagedFunctionFilter<T> filter : filtersList) {
				results = filter.filterObjectsWithDelegate(stage, passingItems, this);
				passingItems = results.valuesForKey(FilterResult.PASS);
			}
		}

		return results;
	}

	/**
	 * Filters using the objects filters only.
	 * 
	 * @param stage
	 * @param sources
	 * @return
	 */
	public final FilterResults<W> filterObjects(StagedFunctionStage stage,
	                                            Iterable<W> sources) {
		FilterResults<W> results = null;
		Iterable<W> passingItems = sources;
		List<StagedFunctionObjectFilter<T, W>> filters = this.objectDependent.valuesForKey(stage);

		if (filters.isEmpty()) {
			results = new FilterResults<W>(sources);
		} else {
			for (StagedFunctionObjectFilter<T, W> filter : filters) {
				results = filter.filterFunctionObjects(stage, passingItems);
				passingItems = results.valuesForKey(FilterResult.PASS);
			}
		}

		return results;
	}

	/**
	 * Filters using all specified filters.
	 * 
	 * @param stage
	 * @param sources
	 * @return
	 */
	public final FilterResults<W> filter(StagedFunctionStage stage,
	                                     Iterable<W> sources) {
		FilterResults<W> results = null;
		Iterable<W> passingItems = sources;

		results = this.filterObjects(stage, passingItems);
		passingItems = results.getPassingObjects();

		List<W> failingItems = results.getFailingObjects();
		results = this.filterNormal(stage, passingItems);
		results.addAll(FilterResult.FAIL, failingItems);

		return results;
	}

	@Override
	public T getModel(W source) {
		return source.getFunctionObject(StagedFunctionStage.FUNCTION_RUNNING);
	}

	@Override
	public T getModel(W source,
	                  StagedFunctionStage stage) {
		return source.getFunctionObject(stage);
	}

	@Override
	public String toString() {
		return "StagedFunctionFiltersMap [normal=" + normal + ", objectDependent=" + objectDependent + "]";
	}

}
