package com.dereekb.gae.server.taskqueue.builder;

import com.dereekb.gae.server.taskqueue.exception.SubmitTaskException;

/**
 * Pre-configured object for building and sending tasks using the input.
 *
 * @author dereekb
 */
public interface TaskRequestSender<T> {

	/**
	 * Creates and submits a task for the input object.
	 *
	 * @param input
	 * @throws SubmitTaskException
	 */
	public void sendTask(T input) throws SubmitTaskException;

	/**
	 * Creates and submits task(s) for the input object.
	 *
	 * @param input
	 * @throws SubmitTaskException
	 */
	public void sendTasks(Iterable<T> input) throws SubmitTaskException;

}
