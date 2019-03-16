package com.dereekb.gae.server.datastore.utility;

/**
 * Factory for {@link StagedUpdater}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface StagedUpdaterFactory<T> {

	/**
	 * Creates a new {@link StagedUpdater} instance.
	 * 
	 * @return {@link StagedUpdater}. Never {@code null}.
	 */
	public StagedUpdater<T> makeUpdater();

}
