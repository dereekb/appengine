package com.dereekb.gae.utilities.task.iteration.impl;

import java.util.Iterator;
import java.util.List;

import com.dereekb.gae.utilities.collections.batch.BatchGenerator;
import com.dereekb.gae.utilities.task.iteration.ConfiguredIterationTask;
import com.dereekb.gae.utilities.task.iteration.IterationTask;
import com.dereekb.gae.utilities.task.iteration.exception.IncompleteTaskIterationException;

/**
 * {@link IterationTask} implementation that uses a
 * {@link ConfiguredIterationTaskImplDelegate} to use the models.
 *
 * The values are batched together using a {@link BatchGenerator} before being
 * passed to the delegate.
 *
 * @author dereekb
 *
 */
public class ConfiguredIterationTaskImpl<T, C>
        implements ConfiguredIterationTask<T, C> {

	private C defaultConfiguration;

	private BatchGenerator<T> batchGenerator;
	private ConfiguredIterationTaskImplDelegate<T, C> delegate;

	public ConfiguredIterationTaskImpl(C defaultConfiguration, ConfiguredIterationTaskImplDelegate<T, C> delegate) {
		this.defaultConfiguration = defaultConfiguration;
		this.delegate = delegate;
		this.batchGenerator = new BatchGenerator<T>();
	}

	@Override
	public void doTask(Iterator<T> input) throws IncompleteTaskIterationException {
		this.doTask(input, this.defaultConfiguration);
	}

	@Override
	public void doTask(Iterator<T> input, C configuration) throws IncompleteTaskIterationException {
		this.delegate.startTask(configuration);

		while (input.hasNext()) {
			List<T> batch = this.batchGenerator.createBatch(input);
			this.delegate.useInputBatch(batch);
		}

		this.delegate.endTask();
	}

	@Override
	public String toString() {
		return "ConfiguredIterationTaskImpl [defaultConfiguration=" + this.defaultConfiguration + ", batchGenerator="
		        + this.batchGenerator + ", delegate=" + this.delegate + "]";
	}

}
