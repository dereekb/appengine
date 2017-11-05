package com.dereekb.gae.server.datastore.utility;

/**
 * Factory for {@link StagedModelDeleter}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface StagedModelDeleterFactory<T> {

	/**
	 * Creates a new {@link StagedModelDeleter} instance.
	 * 
	 * @return {@link StagedModelDeleter}. Never {@code null}.
	 */
	public StagedModelDeleter<T> makeDeleter();

}
