package com.dereekb.gae.model.crud.task.config.impl;

import com.dereekb.gae.model.crud.task.config.DeleteTaskConfig;

/**
 * {@link DeleteTaskConfig} implementation
 *
 * @author dereekb
 *
 */
public class DeleteTaskConfigImpl extends AtomicTaskConfigImpl
        implements DeleteTaskConfig {

	private boolean forceDelete = false;

	public DeleteTaskConfigImpl() {
		super(true);
	}

	public DeleteTaskConfigImpl(boolean isAtomic) {
		super(isAtomic);
	}

	@Override
	public boolean isForceDelete() {
		return this.forceDelete;
	}

	public void setForceDelete(boolean forceDelete) {
		this.forceDelete = forceDelete;
	}

}
