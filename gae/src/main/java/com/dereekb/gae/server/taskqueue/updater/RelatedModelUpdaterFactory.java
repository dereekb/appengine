package com.dereekb.gae.server.taskqueue.updater;

/**
 * Factory for {@link RelatedModelUpdater} instances.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface RelatedModelUpdaterFactory<T> {

	/**
	 * Creates a new updater instance.
	 * 
	 * @return {@link RelatedModelUpdater}. Never {@code null}.
	 */
	public RelatedModelUpdater<T> makeUpdater();

}
