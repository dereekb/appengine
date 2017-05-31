package com.dereekb.gae.utilities.misc.delta.impl;

import com.dereekb.gae.utilities.misc.delta.MutableCountSyncModel;

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

	@Override
	public Long getNonNullDeltaValue() {
		return 0L;
	}

	@Override
	protected Long getDifference(Long a,
	                             Long b) {
		return a - b;
	}

	// MARK: Utility
	public static <T extends MutableCountSyncModel<T, Long>> void initModelCount(T model) {
		model.setRawCount(DEFAULT_COUNT);
		model.setRawDelta(DEFAULT_COUNT);
	}

}
