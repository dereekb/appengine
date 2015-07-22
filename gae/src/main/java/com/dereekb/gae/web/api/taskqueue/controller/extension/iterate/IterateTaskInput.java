package com.dereekb.gae.web.api.taskqueue.controller.extension.iterate;

import java.util.Map;

import com.google.appengine.api.datastore.Cursor;

/**
 * Input for {@link IterateTask} that defines custom parameters,
 *
 * @author dereekb
 *
 */
public interface IterateTaskInput {

	/**
	 * The current task's name.
	 *
	 * @return Task name. Never {@code null}.
	 */
	public String getTaskName();

	/**
	 * The model type.
	 *
	 * @return Model type. Never {@code null}.
	 */
	public String getModelType();

	/**
	 * Step of the iteration.
	 *
	 * @return A non-null positive integer.
	 */
	public Integer getIterationStep();

	/**
	 * Returns the query cursor value, if available.
	 *
	 * @return Query cursor value. {@code null} if not set.
	 */
	public Cursor getQueryCursor();

	/**
	 * @return Parameters for the request that are used for iteration
	 *         configuration. Never {@code null}.
	 */
	public Map<String, String> getParameters();

}
