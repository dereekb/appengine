package com.dereekb.gae.model.crud.task.impl;

import com.dereekb.gae.model.crud.exception.AtomicFunctionException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.task.AtomicTask;
import com.dereekb.gae.model.crud.task.config.AtomicTaskConfig;
import com.dereekb.gae.utilities.task.ConfigurableTask;

/**
 * {@code abstract} class used by {@link ConfigurableTask}'s with
 * {@link AtomicTaskConfig} configuration.
 *
 * @author dereekb
 *
 * @param <P>
 *            pair type
 * @param <C>
 *            configuration type
 */
public abstract class AtomicTaskImpl<P, C extends AtomicTaskConfig>
        implements AtomicTask<P, C> {

	protected C defaultConfig;

	public AtomicTaskImpl(C defaultConfig) {
		this.defaultConfig = defaultConfig;
	}

	public C getDefaultConfig() {
		return this.defaultConfig;
	}

	public void setDefaultConfig(C defaultConfig) {
		this.defaultConfig = defaultConfig;
	}

	// MARK: ConfigurableTask
	@Override
	public void doTask(Iterable<P> input) {
		this.doTask(input, this.defaultConfig);
	}

	@Override
	public void doTask(Iterable<P> input,
	                   C configuration)
	        throws AtomicOperationException {
		if (configuration == null) {
			configuration = this.defaultConfig;
		}

		this.usePairs(input, configuration);
	}

	protected void usePairs(Iterable<P> input,
	                        C configuration)
	        throws AtomicOperationException {
		boolean isAtomic = configuration.isAtomic();
		boolean throwAtomicException = false;

		for (P pair : input) {
			try {
				this.usePair(pair, configuration);
			} catch (AtomicFunctionException e) {
				if (isAtomic) {
					throwAtomicException = true;
				}
			}
		}

		if (throwAtomicException) {
			throw new AtomicOperationException();
		}
	}

	protected abstract void usePair(P pair,
	                                C config)
	        throws AtomicFunctionException;

}
