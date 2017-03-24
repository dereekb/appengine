package com.dereekb.gae.server.datastore.configuration;

/**
 * Configuration for a {@link Getter}.
 * 
 * @author dereekb
 *
 */
public interface GetterConfiguration {

	/**
	 * Whether or not to allow retrieving the value through the cache.
	 * 
	 * @return {@code true} if caching is allowed.
	 */
	public boolean allowCache();

}
