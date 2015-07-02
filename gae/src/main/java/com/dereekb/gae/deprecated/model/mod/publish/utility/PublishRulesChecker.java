package com.thevisitcompany.gae.deprecated.model.mod.publish.utility;

import com.thevisitcompany.gae.deprecated.model.mod.publish.KeyedPublishableModel;

/**
 * Checks to see if the object fits the literal criteria for being published/etc.
 * 
 * This is not for checking specific models,
 * but is used for making sure an object cannot be published again, etc.
 * 
 * @author dereekb
 * 
 * @param <T>
 */
public final class PublishRulesChecker<T extends KeyedPublishableModel<Long>> {

	/**
	 * 
	 * @param model
	 * @return True if the model is not already published.
	 */
	public boolean canPublish(T model) {
		return (model.isPublished() == false);
	}

	/**
	 * 
	 * @return True if the model is published.
	 */
	public boolean canUnpublish(T model) {
		return model.isPublished();
	}

	public boolean hasRequest(T model) {
		return (model.getPublishRequest() != null);
	}

	/**
	 * 
	 * @return True if the model does not have a request
	 */
	public boolean canCreateRequest(T model) {
		return (this.hasRequest(model) == false);
	}

	/**
	 * 
	 * @return True if the model has a request
	 */
	public boolean canCancelRequest(T model) {
		return this.hasRequest(model);
	}

	/**
	 * 
	 * @return True if the model is not already published and doesn't already have a request.
	 */
	public boolean canRequestPublishing(T model) {
		return ((model.isPublished() == false) && (this.hasRequest(model) == false));
	}

	/**
	 * True if the model is currently published or doesn't already have a request.
	 * 
	 * @return
	 */
	public boolean canRequestUnpublishing(T model) {
		return (model.isPublished() && (this.hasRequest(model) == false));
	}

}
