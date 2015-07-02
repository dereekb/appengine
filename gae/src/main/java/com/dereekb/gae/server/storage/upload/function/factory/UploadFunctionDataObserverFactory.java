package com.dereekb.gae.server.storage.upload.function.factory;

import com.dereekb.gae.server.storage.upload.UploadedFile;
import com.dereekb.gae.server.storage.upload.function.UploadFunctionPair;
import com.dereekb.gae.server.storage.upload.function.observers.UploadFunctionDataObserver;
import com.dereekb.gae.server.storage.upload.function.observers.UploadFunctionDataObserverDelegate;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.factory.observer.AbstractStagedFunctionObjectObserverFactory;

public class UploadFunctionDataObserverFactory<T, U extends UploadedFile> extends AbstractStagedFunctionObjectObserverFactory<UploadFunctionDataObserver<T, U>, T, UploadFunctionPair<T, U>> {

	private UploadFunctionDataObserverDelegate<T, U> delegateSingleton;
	private UploadFunctionDataObserverDelegateFactory<T, U> delegateFactory;

	public UploadFunctionDataObserverFactory() {
		super(StagedFunctionStage.POST_SAVING);
	}

	@Override
	public UploadFunctionDataObserver<T, U> generateObserver() {
		UploadFunctionDataObserver<T, U> observer = new UploadFunctionDataObserver<T, U>();

		if (delegateFactory != null) {
			UploadFunctionDataObserverDelegate<T, U> delegate = delegateFactory.makeDataObserverDelegate();
			observer.setDelegate(delegate);
		} else if (delegateSingleton != null) {
			observer.setDelegate(delegateSingleton);
		}

		return observer;
	}

	public UploadFunctionDataObserverDelegateFactory<T, U> getDelegateFactory() {
		return delegateFactory;
	}

	public void setDelegateFactory(UploadFunctionDataObserverDelegateFactory<T, U> delegateFactory) {
		this.delegateFactory = delegateFactory;
	}

	public UploadFunctionDataObserverDelegate<T, U> getDelegateSingleton() {
		return delegateSingleton;
	}

	public void setDelegateSingleton(UploadFunctionDataObserverDelegate<T, U> delegateSingleton) {
		this.delegateSingleton = delegateSingleton;
	}

}
