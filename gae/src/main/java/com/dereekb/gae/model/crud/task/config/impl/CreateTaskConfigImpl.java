package com.dereekb.gae.model.crud.task.config.impl;

import com.dereekb.gae.model.crud.task.config.CreateTaskConfig;

public class CreateTaskConfigImpl extends AtomicTaskConfigImpl
        implements CreateTaskConfig {

	public CreateTaskConfigImpl() {
		super(true);
	}

	public CreateTaskConfigImpl(boolean isAtomic) {
		super(isAtomic);
	}

}
