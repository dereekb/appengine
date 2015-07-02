package com.dereekb.gae.server.taskqueue.deprecated;

import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;

@Deprecated
public class TaskQueuePushRequestHashFilterFactory
        implements Factory<TaskQueuePushRequestHashFilter> {

	private Long defaultCountdown;
	private Long accuracy = 0L;

	@Override
	public TaskQueuePushRequestHashFilter make() throws FactoryMakeFailureException {
		TaskQueuePushRequestHashFilter filter = new TaskQueuePushRequestHashFilter();

		if (this.defaultCountdown != null) {
			filter.setDefaultCountdown(this.defaultCountdown);
		}

		if (this.accuracy != null) {
			filter.setAccuracy(this.accuracy);
		}

		return filter;
	}

	public Long getDefaultCountdown() {
		return this.defaultCountdown;
	}

	public void setDefaultCountdown(Long defaultCountdown) {
		this.defaultCountdown = defaultCountdown;
	}

	public Long getAccuracy() {
		return this.accuracy;
	}

	public void setAccuracy(Long accuracy) {
		this.accuracy = accuracy;
	}

}
