package com.dereekb.gae.server.taskqueue.system.impl;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.utility.converter.impl.TaskRequestConverterImpl;
import com.dereekb.gae.server.taskqueue.scheduler.utility.filter.TaskRequestHashFilterFactory;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.filters.Filter;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * {@link Factory} for {@link TaskRequestSystemImpl}.
 *
 * @author dereekb
 */
@Deprecated
public class TaskRequestSystemImplFactory
        implements Factory<TaskRequestSystemImpl> {

	private String queue;
	private Factory<? extends Filter<TaskRequest>> filterFactory = new TaskRequestHashFilterFactory();
	private DirectionalConverter<TaskRequest, TaskOptions> taskConverter;

	public TaskRequestSystemImplFactory() {}

	public TaskRequestSystemImplFactory(String resource) {
		this(null, resource);
	}

	public TaskRequestSystemImplFactory(String queue, String resource) {
		this.setQueue(queue);
		this.setTaskConverterWithResource(resource);
	}

	public TaskRequestSystemImplFactory(String queue,
	        Factory<? extends Filter<TaskRequest>> filterFactory,
	        DirectionalConverter<TaskRequest, TaskOptions> taskConverter) {
		this.setQueue(queue);
		this.setFilterFactory(filterFactory);
		this.setTaskConverter(taskConverter);
	}

	public String getQueue() {
		return this.queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public Factory<? extends Filter<TaskRequest>> getFilterFactory() {
		return this.filterFactory;
	}

	public void setFilterFactory(Factory<? extends Filter<TaskRequest>> filterFactory) {
		this.filterFactory = filterFactory;
	}

	public DirectionalConverter<TaskRequest, TaskOptions> getTaskConverter() {
		return this.taskConverter;
	}

	public void setTaskConverterWithResource(String resource) {
		this.setTaskConverter(new TaskRequestConverterImpl(resource));
	}

	public void setTaskConverter(DirectionalConverter<TaskRequest, TaskOptions> taskConverter) {
		this.taskConverter = taskConverter;
	}

	@Override
	public TaskRequestSystemImpl make() throws FactoryMakeFailureException {
		TaskRequestSystemImpl system = new TaskRequestSystemImpl();

		if (this.queue != null) {
			system.setQueue(this.queue);
		}

		if (this.taskConverter != null) {
			system.setTaskConverter(this.taskConverter);
		}

		if (this.filterFactory != null) {
			Filter<TaskRequest> filter = this.filterFactory.make();
			system.setFilter(filter);
		}

		return system;
	}

}
