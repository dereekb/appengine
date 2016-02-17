package com.dereekb.gae.model.crud.task.config.impl;

import com.dereekb.gae.model.crud.task.config.UpdateTaskConfig;

public class UpdateTaskConfigImpl extends AtomicTaskConfigImpl
        implements UpdateTaskConfig {

	public UpdateTaskConfigImpl() {
		super(true);
	}

	public UpdateTaskConfigImpl(boolean isAtomic) {
		super(isAtomic);
	}

}
