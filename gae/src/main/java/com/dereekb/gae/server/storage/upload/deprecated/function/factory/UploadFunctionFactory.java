package com.dereekb.gae.server.storage.upload.deprecated.function.factory;

import com.dereekb.gae.server.storage.upload.deprecated.UploadedFile;
import com.dereekb.gae.server.storage.upload.deprecated.function.UploadFunction;
import com.dereekb.gae.server.storage.upload.deprecated.function.UploadFunctionDelegate;
import com.dereekb.gae.server.storage.upload.deprecated.function.UploadFunctionPair;
import com.dereekb.gae.utilities.function.staged.factory.AbstractFilteredStagedFunctionFactory;

@Deprecated
public class UploadFunctionFactory<T, U extends UploadedFile> extends AbstractFilteredStagedFunctionFactory<UploadFunction<T, U>, T, UploadFunctionPair<T, U>> {

	private UploadFunctionDelegate<T> delegate;

	@Override
	protected UploadFunction<T, U> newStagedFunction() {

		UploadFunction<T, U> function = new UploadFunction<T, U>();

		if (this.delegate != null) {
			function.setUploadDelegate(this.delegate);
		}

		return function;
	}

	public UploadFunctionDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(UploadFunctionDelegate<T> delegate) {
		this.delegate = delegate;
	}

}
