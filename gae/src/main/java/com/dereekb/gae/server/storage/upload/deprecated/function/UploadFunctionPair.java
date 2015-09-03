package com.dereekb.gae.server.storage.upload.deprecated.function;

import com.dereekb.gae.server.storage.upload.UploadedFile;
import com.dereekb.gae.utilities.collections.pairs.ResultsPair;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.filter.FallableStagedFunctionObject;

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
		return (successful == false);
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

}