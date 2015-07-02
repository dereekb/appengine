package com.dereekb.gae.model.extension.data.conversion.bytes;

import java.util.Collection;

/**
 * Interface for wrapping and unwrapping objects into a package.
 *
 * @author dereekb
 *
 * @param <T>
 *            Object type.
 * @param <W>
 *            Wrapper type.
 */
public interface ModelPackager<T, W> {

	public W packObjects(Collection<T> objects);

	public Collection<T> unpackObjects(W wrap);

}
