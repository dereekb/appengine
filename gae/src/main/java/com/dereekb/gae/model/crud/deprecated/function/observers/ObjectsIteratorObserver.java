package com.dereekb.gae.model.crud.deprecated.function.observers;

import java.util.List;

import com.dereekb.gae.utilities.function.staged.StagedFunction;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.observer.StagedFunctionObserver;

/**
 * Abstract class that iterates over objects when called.
 *
 * @author dereekb
 *
 * @param <T>
 *            Object type
 */
public abstract class ObjectsIteratorObserver<T>
        implements StagedFunctionObserver<T> {

	@Override
	public void functionHandlerCallback(StagedFunctionStage stage,
	                                    StagedFunction<T, ?> handler) {
		List<T> objects = handler.getFunctionObjects();
		this.iterateItems(objects);
	}

	protected void iterateItems(List<T> objects) {
		for (T object : objects) {
			this.iterateItem(object);
		}
	}

	protected abstract void iterateItem(T object);

}
