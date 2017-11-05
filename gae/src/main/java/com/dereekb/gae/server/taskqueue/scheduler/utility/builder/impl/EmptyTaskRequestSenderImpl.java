package com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl;

import com.dereekb.gae.server.taskqueue.scheduler.exception.SubmitTaskException;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;

/**
 * {@link TaskRequestSender} that does nothing.
 * 
 * @author dereekb
 *
 */
public class EmptyTaskRequestSenderImpl<T>
        implements TaskRequestSender<T> {

	private static EmptyTaskRequestSenderImpl<Object> SINGLETON = new EmptyTaskRequestSenderImpl<Object>();
	
	private EmptyTaskRequestSenderImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	public static <T> EmptyTaskRequestSenderImpl<T> make() {
		return (EmptyTaskRequestSenderImpl<T>) SINGLETON;
	}
	
	// MARK: TaskRequestSender
	@Override
	public void sendTask(T input) throws SubmitTaskException {}

	@Override
	public void sendTasks(Iterable<T> input) throws SubmitTaskException {}

}
