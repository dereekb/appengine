package com.dereekb.gae.server.storage.upload.function.factory;

import com.dereekb.gae.server.storage.upload.UploadedFile;
import com.dereekb.gae.server.storage.upload.function.observers.UploadFunctionDataObserverDelegate;

@Deprecated
public interface UploadFunctionDataObserverDelegateFactory<T, U extends UploadedFile> {

	public UploadFunctionDataObserverDelegate<T, U> makeDataObserverDelegate();

}
