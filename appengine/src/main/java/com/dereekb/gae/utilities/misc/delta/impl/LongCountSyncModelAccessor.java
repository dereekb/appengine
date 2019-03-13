package com.dereekb.gae.utilities.misc.delta.impl;

import com.dereekb.gae.utilities.misc.delta.MutableCountSyncModel;
import com.dereekb.gae.utilities.misc.numbers.Calculator;
import com.dereekb.gae.utilities.misc.numbers.impl.LongCalculator;

/**
 * {@link AbstractCountSyncModelAccessor} implementation for {@link Long}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class LongCountSyncModelAccessor<T extends MutableCountSyncModel<T, Long>> extends AbstractCountSyncModelAccessor<T, Long> {

	public static final Long DEFAULT_COUNT = 1L;

	public LongCountSyncModelAccessor(T model) {
		super(model);
	}

	public static <T extends MutableCountSyncModel<T, Long>> LongCountSyncModelAccessor<T> make(T model) {
		return new LongCountSyncModelAccessor<T>(model);
	}

	// MARK: AbstractCountSyncModelAccessor
	@Override
	public Long getDefaultCount() {
		return DEFAULT_COUNT;
	}

	// MARK: Utility
	public static <T extends MutableCountSyncModel<T, Long>> void initModelCount(T model) {
		model.setRawCount(DEFAULT_COUNT);
		model.setRawDelta(DEFAULT_COUNT);
	}

	@Override
	protected Calculator<Long> getCalculator() {
		return LongCalculator.SINGLETON;
	}

}
