package com.dereekb.gae.utilities.collections.pairs;

public class SuccessResultsPair<T> extends ResultsPair<T, Boolean> {

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

	public boolean hasFailed() {
		return (this.object == false);
	}

	public static <T extends SuccessResultsPair<?>> void setResultPairsSuccess(Iterable<T> pairs,
	                                                                           boolean successful) {
		for (T pair : pairs) {
			pair.setSuccessful(successful);
		}
	}

}
