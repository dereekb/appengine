package com.dereekb.gae.model.crud.task.config.impl;

import com.dereekb.gae.model.crud.task.config.DeleteTaskConfig;

public class DeleteTaskConfigImpl extends AtomicTaskConfigImpl
        implements DeleteTaskConfig {

	public DeleteTaskConfigImpl() {
		super(true);
	}

	public DeleteTaskConfigImpl(boolean isAtomic) {
		super(isAtomic);
	}

}
