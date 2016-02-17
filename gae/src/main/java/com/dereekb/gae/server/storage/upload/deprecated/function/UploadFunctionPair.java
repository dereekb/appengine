package com.dereekb.gae.server.storage.upload.deprecated.function;

import com.dereekb.gae.server.storage.upload.deprecated.UploadedFile;
import com.dereekb.gae.utilities.collections.pairs.ResultsPair;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.filter.FallableStagedFunctionObject;

@Deprecated
public class UploadFunctionPair<T, U extends UploadedFile> extends ResultsPair<U, T>
        implements FallableStagedFunctionObject<T> {

	private boolean successful = true;

	public UploadFunctionPair(U source) {
		super(source);
	}

	@Override
	public T getFunctionObject(StagedFunctionStage stage) {
		T object = this.getResult();
		return object;
	}

	@Override
	public boolean hasFailed() {
		return (this.successful == false);
	}

	public boolean isSuccessful() {
		return this.successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

}