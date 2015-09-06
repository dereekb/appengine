package com.dereekb.gae.server.storage.file.options;

/**
 * Cache options for a file.
 *
 * @author dereekb
 *
 */
public interface StorableFileCacheOptions {

	/**
	 * Whether or not the file should be cached.
	 *
	 * @return {@code true} if the file can be cached.
	 */
	public boolean isCacheable();

	/**
	 * Optional cache time.
	 *
	 * @return {@link Long} cache time. Never {@code null}.
	 */
	public Long getMaxCacheTime();

}
