package com.dereekb.gae.model.extension.links.task;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.model.extension.links.task.LinkModelChangeTaskFactory.ModelLinkChangeTaskDelegate;

/**
 * {@link LinkModelChangeTaskFactory} delegate.
 * 
 * @author dereekb
 *
 */
public interface LinkModelChangeTaskFactoryDelegate<T> {

	/**
	 * Creates a new task delegate.
	 * 
	 * @param input
	 *            {@link IterateTaskInput}. Never {@code null}.
	 * @return {@link ModelLinkChangeTaskDelegate}. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             thrown if a delegate cannot be generated due to illegal
	 *             argument.
	 */
	public ModelLinkChangeTaskDelegate<T> makeTaskDelegate(IterateTaskInput input) throws IllegalArgumentException;

}
