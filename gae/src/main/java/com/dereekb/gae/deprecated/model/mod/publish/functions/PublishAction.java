package com.thevisitcompany.gae.deprecated.model.mod.publish.functions;

public enum PublishAction {

	/**
	 * Requests for the object to be published.
	 */
	PUBLISH_REQUEST,

	/**
	 * Publish the object.
	 */
	PUBLISH,

	/**
	 * Request to unpublish the object
	 */
	UNPUBLISH_REQUEST,

	/**
	 * Unpublish the object
	 */
	UNPUBLISH,

	/**
	 * Cancels any requests made on the object.
	 */
	CANCEL_REQUEST

}
