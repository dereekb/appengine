package com.dereekb.gae.server.storage.upload.deprecated.function.factory;

import com.dereekb.gae.server.storage.upload.deprecated.UploadedFile;
import com.dereekb.gae.server.storage.upload.deprecated.function.observers.UploadFunctionDataObserverDelegate;

@Deprecated
public interface UploadFunctionDataObserverDelegateFactory<T, U extends UploadedFile> {

	public UploadFunctionDataObserverDelegate<T, U> makeDataObserverDelegate();

}
