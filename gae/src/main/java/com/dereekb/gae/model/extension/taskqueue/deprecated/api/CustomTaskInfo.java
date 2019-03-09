package com.dereekb.gae.model.extension.taskqueue.deprecated.api;

import java.util.List;
import java.util.Map;

/**
 * Contains parameters to be fed to a {@link CustomTask}.
 *
 * @author dereekb
 */
@Deprecated
public interface CustomTaskInfo {

	public static final String DEFAULT_TASK_STEP_HEADER = "TQ_TASK_STEP";

	/**
	 * @return the task name/identifier.
	 *
	 *
	 */
	public String getTaskName();

	/**
	 * (Optional) Task step.
	 *
	 * Useful when tasks schedule multiple tasks or a single task strides across
	 * multiple. This step gives an indicator of how many previous steps have
	 * occured.
	 *
	 * @return A non-null positive integer.
	 */
	public Integer getTaskStep();

	/**
	 * (Optional) Collection of model keys that may be specified by the request.
	 *
	 * @return A list of string identifiers. Never null.
	 */
	public List<String> getStringIdentifiers();

	/**
	 * @return All parameters passed along with the request. Never null.
	 */
	public Map<String, String> getKeyedEncodedParameters();

}