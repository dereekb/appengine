package com.dereekb.gae.model.crud.deprecated.function.observers;

import com.dereekb.gae.utilities.function.staged.components.StagedFunctionObject;

/**
 * Delegate used by a {@link ConditionalSaveObjectsObserver}
 *
 * @author dereekb
 *
 * @param <T>
 *            Type of the base object used in the function.
 * @param <W>
 *            Functional Wrapper of the main object that extends
 *            {@link StagedFunctionObject}.
 */
public interface ConditionalSaveObjectsObserverDelegate<T, W extends StagedFunctionObject<T>> {

	/**
	 * Filters the objects that should be saved for the {@link ConditionalSaveObjectsObserver}
	 *
	 * @param objects
	 * @return List of objects to save. Can return null or empty if there are no objects to save.
	 */
	public Iterable<T> filterObjectsToSave(Iterable<W> objects);

}
