package com.dereekb.gae.utilities.function.staged.observer;

import java.util.List;

import com.dereekb.gae.utilities.function.staged.StagedFunction;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStageMap;
import com.dereekb.gae.utilities.function.staged.filter.StagedFunctionFiltersMap;

/**
 * Observer map used by a {@link StagedFunction} for notifying listening Observers.
 * 
 * Allows two types of observers, {@link StagedFunctionObserver} which have access only to the models being worked on, and
 * {@link StagedFunctionObjectObserver} which have access to the set of {@link StagedFunctionObject} that are used by the StagedFunction.
 * 
 * @author dereekb
 *
 * @param <T>
 *            Type of the base object used in this function.
 * @param <W>
 *            Functional Wrapper of the main object that extends StagedFunctionObject.
 * @see {@link StagedFunctionFiltersMap} for filtering objects instead of modifying them.
 */
public final class StagedFunctionObserverMap<T, W extends StagedFunctionObject<T>> extends StagedFunctionStageMap<StagedFunctionObserver<T>, StagedFunctionObjectObserver<T, W>> {

	public StagedFunctionObserverMap() {
		super();
	}

	public void add(StagedFunctionSpecificObserver<T> observer) {
		StagedFunctionStage[] stages = observer.getStages();
		this.add(stages, observer);
	}

	public void add(StagedFunctionSpecificObjectObserver<T, W> observer) {
		StagedFunctionStage[] stages = observer.getStages();
		this.add(stages, observer);
	}

	public void remove(StagedFunctionSpecificObserver<T> observer) {
		StagedFunctionStage[] stages = observer.getStages();
		this.remove(stages, observer);
	}

	public void remove(StagedFunctionSpecificObjectObserver<T, W> observer) {
		StagedFunctionStage[] stages = observer.getStages();
		this.remove(stages, observer);
	}

	public void notifyObserversOfStage(StagedFunctionStage stage,
	                                   StagedFunction<T, W> functionHandler) {
		List<StagedFunctionObserver<T>> observers = this.normal.getElements(stage);
		List<StagedFunctionObjectObserver<T, W>> objectObservers = this.objectDependent.getElements(stage);

		for (StagedFunctionObserver<T> observer : observers) {
			observer.functionHandlerCallback(stage, functionHandler);
		}

		for (StagedFunctionObjectObserver<T, W> observer : objectObservers) {
			observer.functionHandlerCallback(stage, functionHandler);
		}
	}

	@Override
	public String toString() {
		return "StagedFunctionObserverMap [normal=" + normal + ", objectDependent=" + objectDependent + "]";
	}

}
