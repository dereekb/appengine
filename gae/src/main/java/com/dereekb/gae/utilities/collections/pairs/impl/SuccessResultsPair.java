package com.dereekb.gae.utilities.collections.pairs.impl;

import com.dereekb.gae.utilities.collections.pairs.MutableSuccessPair;
import com.dereekb.gae.utilities.collections.pairs.SuccessPair;
import com.dereekb.gae.utilities.misc.success.SuccessModel;

/**
 * {@link MutableSuccessPair} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class SuccessResultsPair<T> extends ResultsPair<T, Boolean> implements SuccessPair<T> {

	public SuccessResultsPair(T source) {
		super(source);
		this.object = false;
	}

	@Override
	public boolean isSuccessful() {
		return this.object;
	}

	public void setSuccessful(boolean successful) {
		this.setResult(successful);
	}

	public boolean hasFailed() {
		return (this.object == false);
	}

	public static <T extends SuccessModel> SuccessPair<T> make(T systemResult) {
		SuccessResultsPair<T> pair = new SuccessResultsPair<T>(systemResult);
		
		boolean success = systemResult.isSuccessful();
		pair.setSuccessful(success);
		
		return pair;
	}
	
	public static <T extends SuccessResultsPair<?>> void setResultPairsSuccess(Iterable<T> pairs,
	                                                                           boolean successful) {
		for (T pair : pairs) {
			pair.setSuccessful(successful);
		}
	}
	
}
