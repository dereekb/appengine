package com.dereekb.gae.utilities.misc.delta.utility.impl;

import com.dereekb.gae.utilities.misc.delta.CountSyncModelAccessor;
import com.dereekb.gae.utilities.misc.delta.MutableCountSyncModel;
import com.dereekb.gae.utilities.misc.delta.impl.AbstractCountSyncModelAccessor;
import com.dereekb.gae.utilities.misc.delta.utility.CountSyncModelSynchronizeCounter;
import com.dereekb.gae.utilities.misc.numbers.Calculator;

/**
 * Abstract {@link CountSyncModelSynchronizeCounter} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <N>
 *            number type
 */
public class AbstractCountSyncModelSynchronizeCounter<T extends MutableCountSyncModel<T, N>, N extends Number>
        implements CountSyncModelSynchronizeCounter<T> {

	protected N delta;
	private final Calculator<N> calculator;

	public AbstractCountSyncModelSynchronizeCounter(Calculator<N> calculator) {
		super();
		this.calculator = calculator;
		this.delta = calculator.zero();
	}

	public N getDelta() {
		return this.delta;
	}

	public void setDelta(N delta) {
		if (delta == null) {
			throw new IllegalArgumentException("delta cannot be null.");
		}

		this.delta = delta;
	}

	// MARK: CountSyncModelSynchronizeCounter
	@Override
	public void synchronize(T model) {
		if (AbstractCountSyncModelAccessor.hasDeltaChange(model)) {
			CountSyncModelAccessor<T, N> accessor = model.getCountAccessor();
			N delta = accessor.getDelta();

			this.addDelta(delta);
			accessor.clearDelta();
		}
	}

	@Override
	public void remove(T model) {
		CountSyncModelAccessor<T, N> accessor = model.getCountAccessor();

		N synchronizedCount = accessor.getSynchronizedCount();
		this.removeDelta(synchronizedCount);
		accessor.resetDeltaToCount();
	}

	// MARL: Calculator
	private void addDelta(N count) {
		this.delta = this.calculator.add(this.delta, count);
	}

	private void removeDelta(N count) {
		this.delta = this.calculator.subtract(this.delta, count);
	}

}
