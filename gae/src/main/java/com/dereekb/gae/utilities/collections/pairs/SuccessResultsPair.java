package com.dereekb.gae.utilities.collections.pairs;

import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.filter.FallableStagedFunctionObject;

public class SuccessResultsPair<T> extends ResultsPair<T, Boolean>
        implements FallableStagedFunctionObject<T> {

	public SuccessResultsPair(T source) {
		super(source);
		this.object = false;
	}

	public boolean isSuccessful() {
		return this.object;
	}

	public void setSuccessful(boolean successful) {
		this.setResult(successful);
	}

	@Override
	public boolean hasFailed() {
		return (this.object == false);
	}

	@Override
	public T getFunctionObject(StagedFunctionStage stage) {
		return this.getSource();
	}

	public static <T extends SuccessResultsPair<?>> void setResultPairsSuccess(Iterable<T> pairs,
	                                                                           boolean successful) {
		for (T pair : pairs) {
			pair.setSuccessful(successful);
		}
	}

}
