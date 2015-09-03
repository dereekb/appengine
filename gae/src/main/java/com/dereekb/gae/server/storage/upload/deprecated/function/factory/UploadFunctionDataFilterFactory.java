package com.dereekb.gae.server.storage.upload.deprecated.function.factory;

import com.dereekb.gae.server.storage.upload.UploadedFile;
import com.dereekb.gae.server.storage.upload.deprecated.function.UploadFunctionPair;
import com.dereekb.gae.server.storage.upload.deprecated.function.filters.UploadFunctionDataFilter;
import com.dereekb.gae.server.storage.upload.deprecated.function.filters.UploadFunctionDataFilterDelegate;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.factory.filter.AbstractStagedFunctionObjectFilterFactory;

@Deprecated
public class UploadFunctionDataFilterFactory<T, U extends UploadedFile> extends AbstractStagedFunctionObjectFilterFactory<UploadFunctionDataFilter<T, U>, T, UploadFunctionPair<T, U>> {

	private UploadFunctionDataFilterDelegate<U> delegate;

	public UploadFunctionDataFilterFactory() {
		super(StagedFunctionStage.STARTED);
	}

	@Override
	public UploadFunctionDataFilter<T, U> generateFilter() {
		UploadFunctionDataFilter<T, U> filter = new UploadFunctionDataFilter<T, U>();

		if (this.delegate != null) {
			filter.setDelegate(this.delegate);
		}

		return filter;
	}

	public UploadFunctionDataFilterDelegate<U> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(UploadFunctionDataFilterDelegate<U> delegate) {
		this.delegate = delegate;
	}

}
