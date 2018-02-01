package com.dereekb.gae.web.taskqueue.server.task.impl.lifecycle;

import java.util.Collection;

import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.web.taskqueue.server.task.impl.AbstractMultiTaskQueueTaskControllerEntry;

/**
 * Abstract task for initializing the server.
 *
 * @author dereekb
 *
 */
public abstract class AbstractServerInitializationTask extends AbstractMultiTaskQueueTaskControllerEntry {

	public static String INITIALIZE_SERVER_TASK_KEY = "initialize_server";

	// MARK: Initialize
	protected abstract class InitializeTask
	        implements TaskEntry {

		@Override
		public String keyValue() {
			return INITIALIZE_SERVER_TASK_KEY;
		}

	}

	protected abstract InitializeTask makeInitializeTask();

	// MARK: Entries
	@Override
	protected Collection<TaskEntry> makeEntries() {
		return ListUtility.toList(this.makeInitializeTask());
	}

}
