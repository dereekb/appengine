package com.dereekb.gae.server.storage.upload.function.factory;

import com.dereekb.gae.server.storage.upload.UploadedFile;
import com.dereekb.gae.server.storage.upload.function.UploadFunction;
import com.dereekb.gae.server.storage.upload.function.UploadFunctionDelegate;
import com.dereekb.gae.server.storage.upload.function.UploadFunctionPair;
import com.dereekb.gae.utilities.function.staged.factory.AbstractFilteredStagedFunctionFactory;

public class UploadFunctionFactory<T, U extends UploadedFile> extends AbstractFilteredStagedFunctionFactory<UploadFunction<T, U>, T, UploadFunctionPair<T, U>> {

	private UploadFunctionDelegate<T> delegate;

	@Override
	protected UploadFunction<T, U> newStagedFunction() {

		UploadFunction<T, U> function = new UploadFunction<T, U>();

		if (delegate != null) {
			function.setUploadDelegate(delegate);
		}

		return function;
	}

	public UploadFunctionDelegate<T> getDelegate() {
		return delegate;
	}

	public void setDelegate(UploadFunctionDelegate<T> delegate) {
		this.delegate = delegate;
	}

}
