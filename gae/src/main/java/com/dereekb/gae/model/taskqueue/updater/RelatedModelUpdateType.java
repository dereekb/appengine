package com.dereekb.gae.model.taskqueue.updater;

/**
 * Types of changes for a {@link RelatedModelUpdater}.
 * 
 * @author dereekb
 *
 */
public enum RelatedModelUpdateType {

	/**
	 * Should update the models in the relation.
	 */
	UPDATE,

	/**
	 * Should remove the models from this relation.
	 */
	REMOVE,

	/**
	 * Should delete the models entirely, and remove the relation.
	 */
	DELETE

}
