package com.dereekb.gae.utilities.model.source.impl;

import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;
import com.dereekb.gae.utilities.model.source.Source;

/**
 * {@link Source} implementation that always returns null.
 * <p>
 * This is against the design of {@link Source}, but might be necessary for some cases.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class NullSourceImpl<T>
        implements Source<T> {

	// MARK: Source
	@Override
	public T loadObject() throws RuntimeException, UnavailableSourceObjectException {
		return null;
	}

}
