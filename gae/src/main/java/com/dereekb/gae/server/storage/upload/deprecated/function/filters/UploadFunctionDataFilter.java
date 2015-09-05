package com.dereekb.gae.server.storage.upload.deprecated.function.filters;

import com.dereekb.gae.server.storage.upload.deprecated.UploadedFile;
import com.dereekb.gae.server.storage.upload.deprecated.function.UploadFunctionPair;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.function.staged.filter.AbstractStagedFunctionObjectFilter;

@Deprecated
public class UploadFunctionDataFilter<T, U extends UploadedFile> extends AbstractStagedFunctionObjectFilter<T, UploadFunctionPair<T, U>> {

	private UploadFunctionDataFilterDelegate<U> delegate;

	@Override
	public FilterResult filterObject(UploadFunctionPair<T, U> object) {
		U file = object.getSource();
		boolean isValid = this.delegate.isValidData(file);
		FilterResult result = FilterResult.withBoolean(isValid);
		return result;
	}

	public UploadFunctionDataFilterDelegate<U> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(UploadFunctionDataFilterDelegate<U> delegate) {
		this.delegate = delegate;
	}

}
