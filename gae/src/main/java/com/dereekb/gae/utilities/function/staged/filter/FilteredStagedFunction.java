package com.dereekb.gae.utilities.function.staged.filter;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.utilities.filters.FilterResults;
import com.dereekb.gae.utilities.function.staged.StagedFunction;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.delegates.StagedFunctionDelegate;
import com.dereekb.gae.utilities.function.staged.delegates.StagedFunctionSaveDelegate;
import com.dereekb.gae.utilities.function.staged.exception.StagedFunctionRunningException;

/**
 * Functions handler that has additional support for filtering at each stage.
 *
 * Filters are called before observers are called.
 *
 * Filters can either access objects, or the StagedFunctionObject directly.
 *
 * @author dereekb
 *
 * @param <T>
 *            Object type
 * @param <W>
 *            StagedFunctionObject with the object type <T>
 */
public abstract class FilteredStagedFunction<T, W extends StagedFunctionObject<T>> extends StagedFunction<T, W> {

	private boolean suppressFilters = false;

	private StagedFunctionFiltersMap<T, W> filterMap;
	private FilteredStagedFunctionObserver<T, W> filterObserver;

	public FilteredStagedFunction() {
		this(null, null, null);
	}

	public FilteredStagedFunction(StagedFunctionSaveDelegate<T> saveDelegate) {
		this(saveDelegate, null, null);
	}

	public FilteredStagedFunction(StagedFunctionSaveDelegate<T> saveDelegate,
	        StagedFunctionFiltersMap<T, W> filterMap) {
		this(saveDelegate, null, filterMap);
	}

	public FilteredStagedFunction(StagedFunctionSaveDelegate<T> saveDelegate, StagedFunctionDelegate<T> delegate) {
		this(saveDelegate, delegate, null);
	}

	public FilteredStagedFunction(StagedFunctionSaveDelegate<T> saveDelegate,
	        StagedFunctionDelegate<T> delegate,
	        StagedFunctionFiltersMap<T, W> filterMap) {
		super(saveDelegate, delegate);

		if (filterMap != null) {
			this.setFilterMap(filterMap);
		} else {
			this.setFilterMap(new StagedFunctionFiltersMap<T, W>());
		}
	}

	@Override
	protected void notifyObserversOfStage(StagedFunctionStage stage) {
		this.notifyFiltersOfStage(stage);
		super.notifyObserversOfStage(stage);
	}

	protected void notifyFiltersOfStage(StagedFunctionStage stage) {
		boolean isRunning = this.isRunning();

		if (isRunning && this.suppressFilters == false) {
			Collection<W> objects = this.getWorkingObjects();
			FilterResults<W> results = this.filterMap.filter(stage, objects);

			List<W> passingObjects = results.getPassingObjects();
			List<W> failingObjects = results.getFailingObjects();

			if (this.filterObserver != null && (failingObjects.isEmpty() == false)) {
				this.filterObserver.objectsWereFilteredOut(failingObjects, stage);
			}

			this.setWorkingObjects(passingObjects);
		}
	}

	@Override
	protected void started() {
		this.filterMap.prepareFilters();
		super.started();
	}

	@Override
	protected void completed() {
		super.completed();
		this.suppressFilters = false;
	}

	@Override
	protected void setCurrentStage(StagedFunctionStage stage) {
		this.filterMap.setStage(stage);
		super.setCurrentStage(stage);
	}

	public final StagedFunctionFiltersMap<T, W> getFiltersMap() {
		return this.filterMap;
	}

	public final void setFilterMap(StagedFunctionFiltersMap<T, W> filterMap) {
		if (this.isRunning()) {
			throw new StagedFunctionRunningException();
		} else {
			if (filterMap == null) {
				throw new NullPointerException("Cannot set filters map to null.");
			}

			this.filterMap = filterMap;
		}
	}

	protected final boolean isSuppressFilters() {
		return this.suppressFilters;
	}

	protected final void setSuppressFilters(boolean suppressFilters) {
		this.suppressFilters = suppressFilters;
	}

	/**
	 * @return Returns true if any object has been filtered out.
	 */
	protected final boolean hasFilteredOutObjects() {
		boolean sameSize = (this.getWorkingObjectsCount() == this.getObjectsCount());
		return (sameSize == false);
	}

	public FilteredStagedFunctionObserver<T, W> getFilterObserver() {
		return this.filterObserver;
	}

	public void setFilterObserver(FilteredStagedFunctionObserver<T, W> filterObserver) {
		this.filterObserver = filterObserver;
	}

}
