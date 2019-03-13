package com.dereekb.gae.utilities.misc.delta.utility.impl;

import com.dereekb.gae.utilities.misc.delta.MutableCountSyncModel;
import com.dereekb.gae.utilities.misc.numbers.impl.LongCalculator;

/**
 * {@link AbstractCountSyncModelSynchronizeCounter} extension for {@link Long}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class LongCountSyncModelSynchronizeCounter<T extends MutableCountSyncModel<T, Long>> extends AbstractCountSyncModelSynchronizeCounter<T, Long> {

	public LongCountSyncModelSynchronizeCounter() {
		super(LongCalculator.SINGLETON);
	}

}
