package com.dereekb.gae.model.crud.deprecated.function.observers;

import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.dereekb.gae.utilities.function.staged.StagedFunction;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.observer.StagedFunctionObjectObserver;

/**
 * Observer used to save objects during an observation phase rather than at the
 * save phase in a {@link StagedFunction} as usual.
 *
 * @author dereekb
 *
 * @param <T>
 *            Type of the base object used in the function.
 * @param <W>
 *            Functional Wrapper of the main object that extends
 *            {@link StagedFunctionObject}.
 */
public class ConditionalSaveObjectsObserver<T, W extends StagedFunctionObject<T>>
        implements StagedFunctionObjectObserver<T, W> {

	private ConfiguredSetter<T> setter;
	private ConditionalSaveObjectsObserverDelegate<T, W> delegate;

	@Override
	public void functionHandlerCallback(StagedFunctionStage stage,
	                                    StagedFunction<T, W> handler) {
		Iterable<W> objects = handler.getWorkingObjectsIterator();
		Iterable<T> filteredObjects = this.delegate.filterObjectsToSave(objects);

		if (filteredObjects != null) {
			this.setter.save(filteredObjects);
		}
	}

	public ConfiguredSetter<T> getSetter() {
		return this.setter;
	}

	public void setSetter(ConfiguredSetter<T> setter) {
		this.setter = setter;
	}

	public ConditionalSaveObjectsObserverDelegate<T, W> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(ConditionalSaveObjectsObserverDelegate<T, W> delegate) {
		this.delegate = delegate;
	}

}
