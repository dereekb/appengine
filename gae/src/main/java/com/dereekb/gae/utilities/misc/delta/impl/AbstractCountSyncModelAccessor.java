package com.dereekb.gae.utilities.misc.delta.impl;

import com.dereekb.gae.utilities.misc.delta.CountSyncModelAccessor;
import com.dereekb.gae.utilities.misc.delta.MutableCountSyncModel;

/**
 * Abstract implementation of {@link CountSyncModelAccessor}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <N>
 *            count number type
 */
public abstract class AbstractCountSyncModelAccessor<T extends MutableCountSyncModel<T, N>, N extends Number>
        implements CountSyncModelAccessor<T, N> {

	private final T model;

	public AbstractCountSyncModelAccessor(T model) {
		super();
		this.model = model;
	}

	// MARK: CountSyncModelAccessor
	@Override
	public void initCount() {
		N defaultCount = this.getDefaultCount();

		this.model.setRawCount(defaultCount);
		this.model.setRawDelta(defaultCount);
	}

	@Override
	public N getSynchronizedCount() {
		return this.getDifference(this.getCount(), this.getNonNullDelta());
	}

	@Override
	public T getModel() {
		return this.model;
	}

	@Override
	public boolean hasDeltaChange() {
		return this.getDelta() != null;
	}

	@Override
	public N getNonNullDelta() {
		N delta = this.getDelta();
		return (delta != null) ? delta : this.getNonNullDelta();
	}

	@Override
	public N getCount() {
		return this.model.getCount();
	}

	@Override
	public N getDelta() {
		return this.model.getDelta();
	}

	@Override
	public void setCount(N count) {
		if (count == null) {
			count = this.getDefaultCount();
		}

		this.updateCountAndDelta(count);
	}

	@Override
	public void resetDeltaToCount() {
		this.model.setRawDelta(this.getCount());
	}

	@Override
	public void clearDelta() {
		this.model.setRawDelta(null);
	}

	// MARK: Internal
	public abstract N getDefaultCount();

	protected abstract N getNonNullDeltaValue();

	protected void updateCountAndDelta(N count) {
		if (count.equals(this.getCount())) {
			return;	// No changes needed.
		}

		N original = this.getSynchronizedCount();
		N delta = this.getDifference(count, original);

		this.model.setRawCount(count);
		this.model.setRawDelta(delta);
	}

	protected abstract N getDifference(N a,
	                                   N b);

}
