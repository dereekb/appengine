package com.thevisitcompany.gae.deprecated.model.mod.publish.utility;

/**
 * Checks whether or not models of type T are eligible for publishing actions.
 * 
 * @author dereekb
 * 
 * @param <T>
 */
public interface PublishRulesDelegate<T> {

	/**
	 * Checks whether or not the given model is eligible for publishing. This includes the ability to create a publish
	 * request.
	 * 
	 * @param publishable
	 * @return
	 */
	public boolean canPublish(T publishable);

	/**
	 * Checks whether or not the given model is eligible for unpublishing. This includes the ability to create an
	 * unpublish request.
	 * 
	 * @param publishable
	 * @return
	 */
	public boolean canUnpublish(T publishable);

}
