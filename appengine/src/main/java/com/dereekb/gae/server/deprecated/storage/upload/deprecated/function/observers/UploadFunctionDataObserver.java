package com.dereekb.gae.server.storage.upload.deprecated.function.observers;

import com.dereekb.gae.server.storage.upload.deprecated.UploadedFile;
import com.dereekb.gae.server.storage.upload.deprecated.function.UploadFunctionPair;
import com.dereekb.gae.utilities.function.staged.StagedFunction;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.observer.StagedFunctionObjectObserver;

/**
 * Wraps a UploadFunctionDataObserverDelegate that handles the input data.
 *
 * @author dereekb
 *
 * @param <T>
 * @param <U>
 */
@Deprecated
public class UploadFunctionDataObserver<T, U extends UploadedFile>
        implements StagedFunctionObjectObserver<T, UploadFunctionPair<T, U>> {

	private UploadFunctionDataObserverDelegate<T, U> delegate;

	@Override
	public void functionHandlerCallback(StagedFunctionStage stage,
	                                    StagedFunction<T, UploadFunctionPair<T, U>> handler) {
		Iterable<UploadFunctionPair<T, U>> pairs = handler.getWorkingObjectsIterator();
		this.delegate.handleUploadedData(pairs);
	}

	public UploadFunctionDataObserverDelegate<T, U> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(UploadFunctionDataObserverDelegate<T, U> delegate) {
		this.delegate = delegate;
	}

}