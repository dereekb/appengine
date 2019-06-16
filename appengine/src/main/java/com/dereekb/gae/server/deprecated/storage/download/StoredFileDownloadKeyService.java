package com.dereekb.gae.server.storage.download;

import com.dereekb.gae.server.deprecated.storage.exception.MissingFileException;
import com.dereekb.gae.server.deprecated.storage.object.file.Storable;

/**
 * Interface for generating download keys for a storable type.
 *
 * @author dereekb
 *
 */
@Deprecated
public interface StoredFileDownloadKeyService<T> {

	/**
	 * Generates a new download key for the given item.
	 *
	 * The keys themselves may not mean anything by themselves, and do not necessarily explain what indexService they are
	 * for.
	 *
	 * @param source
	 * @param item
	 * @return
	 */
	public String makeDownloadKey(T source,
	                              Storable item) throws MissingFileException;

}
