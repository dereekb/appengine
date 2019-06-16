package com.dereekb.gae.server.storage.object.path;

import com.dereekb.gae.server.deprecated.storage.object.file.Storable;
import com.dereekb.gae.server.deprecated.storage.object.file.StorableFile;
import com.dereekb.gae.server.deprecated.storage.object.file.StorableFilePathResolver;

/**
 * Used for retrieving a {@link StorableFile} of a {@link Storable} reference,
 * relative to another object.
 *
 * @author dereekb
 *
 * @param <T>
 *            object type
 * @deprecated Use {@link StorableFilePathResolver} instead.
 */
@Deprecated
public interface RelativeFilePathResolver<T> {

	public StorableFile pathForStorable(T relative,
	                                    Storable reference);

}
