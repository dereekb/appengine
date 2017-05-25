package com.dereekb.gae.server.taskqueue.updater;

/**
 * Used for updating a relation between the input model type and all other
 * related objects.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface RelatedModelUpdater<T> {

	/**
	 * Updates all relations for the input models.
	 * 
	 * @param change
	 *            {@link RelatedModelUpdateType}. Never {@code null}.
	 * @param models
	 *            {@link Iterable} of models. Never {@code null}.
	 * 
	 * @return {@link RelatedModelUpdaterResult}. Never
	 *         {@code null}.
	 */
	public RelatedModelUpdaterResult updateRelations(RelatedModelUpdateType change,
	                                                 Iterable<T> models);

}
