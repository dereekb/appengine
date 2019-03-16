package com.dereekb.gae.model.extension.data.conversion.bytes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Default implementation of {@link ModelPackager} that wraps objects in an
 * {@link List}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class ListModelPackager<T>
        implements ModelPackager<T, List<T>> {

	@Override
	public ArrayList<T> packObjects(Collection<T> objects) {
		return new ArrayList<T>(objects);
	}

	@Override
	public Collection<T> unpackObjects(List<T> wrap) {
		return wrap;
	}

}
