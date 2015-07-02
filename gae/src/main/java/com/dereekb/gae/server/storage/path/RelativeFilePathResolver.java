package com.dereekb.gae.server.storage.path;

import com.dereekb.gae.server.storage.file.Storable;
import com.dereekb.gae.server.storage.file.StorableFile;

/**
 * Used for retrieving a {@link StorableFile} of a {@link Storable} reference,
 * relative to another object.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface RelativeFilePathResolver<T> {

	public StorableFile pathForStorable(T relative,
	                                    Storable reference);

}
