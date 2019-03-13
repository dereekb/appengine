package com.dereekb.gae.server.taskqueue.deprecated;

import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.filters.Filter;

@Deprecated
public class TaskQueueManagerFactory
        implements Factory<TaskQueueManager> {

	private String queueName = null;
	private String baseUrl = null;
	private Long defaultCountdown = null;
	private Filter<TaskQueuePushRequest> filter;
	private Factory<Filter<TaskQueuePushRequest>> filterFactory;

	@Override
	public TaskQueueManager make() {
		TaskQueueManager manager = new TaskQueueManager();

		manager.setBaseUrl(this.baseUrl);
		manager.setQueueName(this.queueName);
		manager.setDefaultCountdown(this.defaultCountdown);

		if (this.filterFactory != null) {
			Filter<TaskQueuePushRequest> filter = this.filterFactory.make();
			manager.setFilter(filter);
		} else if (this.filter != null) {
			manager.setFilter(this.filter);
		}

		return manager;
	}

	public String getQueueName() {
		return this.queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getBaseUrl() {
		return this.baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public Long getDefaultCountdown() {
		return this.defaultCountdown;
	}

	public void setDefaultCountdown(Long defaultCountdown) {
		this.defaultCountdown = defaultCountdown;
	}

	public Filter<TaskQueuePushRequest> getFilter() {
		return this.filter;
	}

	public void setFilter(Filter<TaskQueuePushRequest> filter) {
		this.filter = filter;
	}

	public Factory<Filter<TaskQueuePushRequest>> getFilterFactory() {
		return this.filterFactory;
	}

	public void setFilterFactory(Factory<Filter<TaskQueuePushRequest>> filterFactory) {
		this.filterFactory = filterFactory;
	}

}
