package com.dereekb.gae.server.datastore.utility;

/**
 * Factory for {@link StagedStorer}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface StagedStorerFactory<T> {

	/**
	 * Creates a new {@link StagedStorer} instance.
	 * 
	 * @return {@link StagedStorer}. Never {@code null}.
	 */
	public StagedStorer<T> makeStorer();

}
