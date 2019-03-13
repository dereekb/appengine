package com.dereekb.gae.server.datastore.utility.impl;

import com.dereekb.gae.server.datastore.utility.StagedTransactionAlreadyFinishedException;
import com.dereekb.gae.server.datastore.utility.StagedTransactionChange;
import com.dereekb.gae.server.datastore.utility.StagedTransactionChangeResult;

/**
 * {@link StagedTransactionChangeResult} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 */
public class StagedTransactionChangeResultImpl<T>
        implements StagedTransactionChangeResult<T> {

	private T result;
	private StagedTransactionChange change;

	public StagedTransactionChangeResultImpl(T result, StagedTransactionChange change) {
		super();
		this.setResult(result);
		this.setChange(change);
	}

	public static <T> StagedTransactionChangeResult<T> make(T value,
	                                                        StagedTransactionChange change) {
		return new StagedTransactionChangeResultImpl<T>(value, change);
	}

	// MARK: StagedTransactionChangeResult
	@Override
	public T getResult() {
		return this.result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public StagedTransactionChange getChange() {
		return this.change;
	}

	public void setChange(StagedTransactionChange change) {
		if (change == null) {
			throw new IllegalArgumentException("change cannot be null.");
		}

		this.change = change;
	}

	@Override
	public void finishChanges() throws StagedTransactionAlreadyFinishedException {
		this.change.finishChanges();
	}

	@Override
	public String toString() {
		return "StagedTransactionChangeResultImpl [result=" + this.result + ", change=" + this.change + "]";
	}

}
