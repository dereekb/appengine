package com.dereekb.gae.model.crud.task.config.impl;

import com.dereekb.gae.model.crud.services.request.options.impl.AtomicRequestOptionsImpl;
import com.dereekb.gae.model.crud.task.config.AtomicTaskConfig;

/**
 * {@link AtomicTaskConfig} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AtomicTaskConfigImpl extends AtomicRequestOptionsImpl
        implements AtomicTaskConfig {

	public AtomicTaskConfigImpl() {
		super();
	}

	public AtomicTaskConfigImpl(boolean atomic) {
		super(atomic);
	}

}
