package com.dereekb.gae.model.crud.deprecated.function.observers.factory;

import com.dereekb.gae.model.crud.deprecated.function.observers.ConditionalSaveObjectsObserver;
import com.dereekb.gae.model.crud.deprecated.function.observers.ConditionalSaveObjectsObserverDelegate;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.factory.observer.AbstractStagedFunctionObjectObserverFactory;

public class ConditionalSaveObjectsObserverFactory<T, W extends StagedFunctionObject<T>> extends AbstractStagedFunctionObjectObserverFactory<ConditionalSaveObjectsObserver<T, W>, T, W> {

	private ConfiguredSetter<T> setter;

	private ConditionalSaveObjectsObserverDelegate<T, W> delegate;
	private Factory<ConditionalSaveObjectsObserverDelegate<T, W>> delegateFactory;

	public ConditionalSaveObjectsObserverFactory() {
		super(StagedFunctionStage.FINISHED);
	}

	@Override
	public ConditionalSaveObjectsObserver<T, W> generateObserver() {
		ConditionalSaveObjectsObserver<T, W> observer = new ConditionalSaveObjectsObserver<T, W>();

		if (this.setter != null) {
			observer.setSetter(this.setter);
		}

		ConditionalSaveObjectsObserverDelegate<T, W> delegate = null;

		if (this.delegateFactory != null) {
			delegate = this.delegateFactory.make();
		} else if (this.delegate != null) {
			delegate = this.delegate;
		}

		if (delegate != null) {
			observer.setDelegate(delegate);
		}

		return observer;
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

	public Factory<ConditionalSaveObjectsObserverDelegate<T, W>> getDelegateFactory() {
		return this.delegateFactory;
	}

	public void setDelegateFactory(Factory<ConditionalSaveObjectsObserverDelegate<T, W>> delegateFactory) {
		this.delegateFactory = delegateFactory;
	}

}
