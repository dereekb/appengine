package com.dereekb.gae.model.extension.iterate;

import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;

/**
 * Input for {@link IterateTaskExecutor} that defines custom parameters,
 *
 * @author dereekb
 *
 */
public interface IterateTaskInput extends TypedModel {

	/**
	 * The current task's name.
	 *
	 * @return Task name. Never {@code null}.
	 */
	public String getTaskName();

	/**
	 * @return Parameters for the request that are used for iteration
	 *         configuration. Never {@code null}.
	 */
	public CaseInsensitiveMap<String> getParameters();

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
