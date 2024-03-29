package com.dereekb.gae.server.storage.object.file.options;


/**
 * Generic file options.
 *
 * Contains options for visibility and public caching.
 *
 * @author dereekb
 */
public interface StorableFileOptions {

	public StorableFileVisibility getVisibility();

	public StorableFileCacheOptions getCacheOptions();

}
