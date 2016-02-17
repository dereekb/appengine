package com.dereekb.gae.model.crud.task.config.impl;

import com.dereekb.gae.model.crud.task.config.AtomicTaskConfig;

/**
 * {@link AtomicTaskConfig} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AtomicTaskConfigImpl
        implements AtomicTaskConfig {

	private boolean isAtomic;

	public AtomicTaskConfigImpl(boolean isAtomic) {
		this.isAtomic = isAtomic;
	}

	@Override
	public boolean isAtomic() {
		return this.isAtomic;
	}

	public void setAtomic(boolean isAtomic) {
		this.isAtomic = isAtomic;
	}

}
