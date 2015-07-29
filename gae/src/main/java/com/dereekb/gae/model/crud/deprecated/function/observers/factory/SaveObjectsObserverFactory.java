package com.dereekb.gae.model.crud.deprecated.function.observers.factory;

import com.dereekb.gae.model.crud.deprecated.function.observers.SaveObjectsObserver;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.factory.observer.AbstractStagedFunctionObserverFactory;

/**
 * Factory for generating {@link SaveObjectsObserver} instances.
 *
 * @author dereekb
 * @param <T>
 */
public class SaveObjectsObserverFactory<T> extends AbstractStagedFunctionObserverFactory<SaveObjectsObserver<T>, T> {

	private ConfiguredSetter<T> setter;

	public SaveObjectsObserverFactory() {
		super(StagedFunctionStage.FINISHED);
	}

	@Override
	public SaveObjectsObserver<T> generateObserver() {
		return new SaveObjectsObserver<T>(this.setter);
	}

	public ConfiguredSetter<T> getSetter() {
		return this.setter;
	}

	public void setSetter(ConfiguredSetter<T> setter) {
		this.setter = setter;
	}

}
