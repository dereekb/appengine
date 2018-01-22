package com.dereekb.gae.web.taskqueue.server.impl;

import com.dereekb.gae.utilities.collections.map.impl.LazyKeyedCaseInsensitiveEntryContainer;
import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.web.taskqueue.server.TaskQueueTaskControllerEntry;
import com.dereekb.gae.web.taskqueue.server.TaskQueueTaskControllerRequest;
import com.dereekb.gae.web.taskqueue.server.impl.AbstractMultiTaskQueueTaskControllerEntry.TaskEntry;

/**
 * Abstract {@link TaskQueueTaskControllerEntry} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractMultiTaskQueueTaskControllerEntry extends LazyKeyedCaseInsensitiveEntryContainer<TaskEntry>
        implements TaskQueueTaskControllerEntry {

	protected interface TaskEntry
	        extends Task<TaskQueueTaskControllerRequest>, AlwaysKeyed<String> {}

	// MARK: TaskQueueTaskControllerEntry
	@Override
	public void performTask(TaskQueueTaskControllerRequest request) throws RuntimeException {
		String name = request.getTaskName();
		TaskEntry entry = this.getEntryForType(name);
		entry.doTask(request);
	}

}
