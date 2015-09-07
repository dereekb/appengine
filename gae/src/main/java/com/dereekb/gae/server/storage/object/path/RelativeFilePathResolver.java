package com.dereekb.gae.server.storage.object.path;

import com.dereekb.gae.server.storage.object.file.Storable;
import com.dereekb.gae.server.storage.object.file.StorableFile;

/**
 * Used for retrieving a {@link StorableFile} of a {@link Storable} reference,
 * relative to another object.
 *
 * @author dereekb
 *
 * @param <T>
 *            object type
 */
public interface RelativeFilePathResolver<T> {

	public StorableFile pathForStorable(T relative,
	                                    Storable reference);

}
