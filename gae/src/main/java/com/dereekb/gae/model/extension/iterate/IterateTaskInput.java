package com.dereekb.gae.model.extension.iterate;

import java.util.Map;

/**
 * Input for {@link IterateTaskExecutor} that defines custom parameters,
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
	 * @return Parameters for the request that are used for iteration
	 *         configuration. Never {@code null}.
	 */
	public Map<String, String> getParameters();

	/**
	 * Step of the iteration.
	 *
	 * @return A non-null positive integer.
	 */
	public Integer getIterationStep();

	/**
	 * Web-safe string of the cursor used.
	 *
	 * @return Query cursor value. {@code null} if not set.
	 */
	public String getStepCursor();

}
