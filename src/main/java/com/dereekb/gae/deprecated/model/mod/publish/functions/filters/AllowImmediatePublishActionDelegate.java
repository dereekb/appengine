package com.thevisitcompany.gae.deprecated.model.mod.publish.functions.filters;

import com.thevisitcompany.gae.deprecated.model.mod.publish.functions.PublishAction;

/**
 * Filter that checks whether or not the publish action can occur immediately.
 * 
 * @author dereekb
 * 
 */
public interface AllowImmediatePublishActionDelegate<T> {

	/**
	 * Checks whether or not the action can be done immediately or not.
	 * 
	 * For example, this means being able to publish right away as opposed to requiring a request first be made.
	 * 
	 * @param action Publish Action to check (Publish/Unpublish)
	 * @param model Model to check.
	 * 
	 * @return True if the given action can be executed immediately.
	 */
	public boolean canDoActionImmediately(PublishAction action,
	                                      T model);

}
