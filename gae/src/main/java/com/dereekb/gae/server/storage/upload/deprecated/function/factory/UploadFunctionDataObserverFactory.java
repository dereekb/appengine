package com.dereekb.gae.server.storage.upload.deprecated.function.factory;

import com.dereekb.gae.server.storage.upload.UploadedFile;
import com.dereekb.gae.server.storage.upload.deprecated.function.UploadFunctionPair;
import com.dereekb.gae.server.storage.upload.deprecated.function.observers.UploadFunctionDataObserver;
import com.dereekb.gae.server.storage.upload.deprecated.function.observers.UploadFunctionDataObserverDelegate;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.factory.observer.AbstractStagedFunctionObjectObserverFactory;

@Deprecated
public class UploadFunctionDataObserverFactory<T, U extends UploadedFile> extends AbstractStagedFunctionObjectObserverFactory<UploadFunctionDataObserver<T, U>, T, UploadFunctionPair<T, U>> {

	private UploadFunctionDataObserverDelegate<T, U> delegateSingleton;
	private UploadFunctionDataObserverDelegateFactory<T, U> delegateFactory;

	public UploadFunctionDataObserverFactory() {
		super(StagedFunctionStage.POST_SAVING);
	}

	@Override
	public UploadFunctionDataObserver<T, U> generateObserver() {
		UploadFunctionDataObserver<T, U> observer = new UploadFunctionDataObserver<T, U>();

		if (this.delegateFactory != null) {
			UploadFunctionDataObserverDelegate<T, U> delegate = this.delegateFactory.makeDataObserverDelegate();
			observer.setDelegate(delegate);
		} else if (this.delegateSingleton != null) {
			observer.setDelegate(this.delegateSingleton);
		}

		return observer;
	}

	public UploadFunctionDataObserverDelegateFactory<T, U> getDelegateFactory() {
		return this.delegateFactory;
	}

	public void setDelegateFactory(UploadFunctionDataObserverDelegateFactory<T, U> delegateFactory) {
		this.delegateFactory = delegateFactory;
	}

	public UploadFunctionDataObserverDelegate<T, U> getDelegateSingleton() {
		return this.delegateSingleton;
	}

	public void setDelegateSingleton(UploadFunctionDataObserverDelegate<T, U> delegateSingleton) {
		this.delegateSingleton = delegateSingleton;
	}

}
