package com.dereekb.gae.server.taskqueue.scheduler.utility.filter;

import com.dereekb.gae.server.taskqueue.scheduler.utility.filter.impl.TaskRequestHashBuilderImpl;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;

/**
 * {@link Factory} for {@link TaskRequestHashFilter}.
 *
 * @author dereekb
 */
@Deprecated
public class TaskRequestHashFilterFactory
        implements Factory<TaskRequestHashFilter> {

	private TaskRequestHashBuilder hashBuilder = new TaskRequestHashBuilderImpl();
	private Long defaultCountdown = null;
	private Long accuracy = 0L;

	@Override
	public TaskRequestHashFilter make() throws FactoryMakeFailureException {
		TaskRequestHashFilter filter = new TaskRequestHashFilter(this.hashBuilder, this.defaultCountdown, this.accuracy);
		return filter;
	}

}
