package com.dereekb.gae.utilities.task.iteration.impl;

import java.util.Iterator;
import java.util.List;

import com.dereekb.gae.utilities.collections.batch.impl.PartitionerImpl;
import com.dereekb.gae.utilities.task.iteration.ConfiguredIterationTask;
import com.dereekb.gae.utilities.task.iteration.IterationTask;
import com.dereekb.gae.utilities.task.iteration.exception.IncompleteTaskIterationException;

/**
 * {@link IterationTask} implementation that uses a
 * {@link ConfiguredIterationTaskImplDelegate} to use the models.
 * <p>
 * The values are batched together using a {@link PartitionerImpl} before
 * being passed to the delegate.
 *
 * @author dereekb
 *
 */
public class ConfiguredIterationTaskImpl<T, C>
        implements ConfiguredIterationTask<T, C> {

	private C defaultConfiguration;

	private PartitionerImpl partitioner = new PartitionerImpl();
	private ConfiguredIterationTaskImplDelegate<T, C> delegate;

	public ConfiguredIterationTaskImpl(C defaultConfiguration, ConfiguredIterationTaskImplDelegate<T, C> delegate) {
		this.defaultConfiguration = defaultConfiguration;
		this.delegate = delegate;
	}

	public C getDefaultConfiguration() {
		return this.defaultConfiguration;
	}

	public void setDefaultConfiguration(C defaultConfiguration) {
		this.defaultConfiguration = defaultConfiguration;
	}

	public PartitionerImpl getPartitioner() {
		return this.partitioner;
	}

	public void setPartitioner(PartitionerImpl partitioner) {
		this.partitioner = partitioner;
	}

	public ConfiguredIterationTaskImplDelegate<T, C> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(ConfiguredIterationTaskImplDelegate<T, C> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void doTask(Iterator<T> input) throws IncompleteTaskIterationException {
		this.doTask(input, this.defaultConfiguration);
	}

	@Override
	public void doTask(Iterator<T> input,
	                   C configuration) throws IncompleteTaskIterationException {
		this.delegate.startTask(configuration);

		while (input.hasNext()) {
			List<T> partition = this.partitioner.cutPartition(input);
			this.delegate.useInputBatch(partition);
		}

		this.delegate.endTask();
	}

}
