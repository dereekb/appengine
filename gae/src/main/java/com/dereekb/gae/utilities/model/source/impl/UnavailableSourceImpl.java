package com.dereekb.gae.utilities.model.source.impl;

import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;
import com.dereekb.gae.utilities.model.source.Source;

/**
 * {@link Source} implementation that always throws
 * {@link UnavailableSourceObjectException}.
 * 
 * @author dereekb
 *
 */
public class UnavailableSourceImpl<T>
        implements Source<T> {

	private static final Source<?> SINGLETON = new UnavailableSourceImpl<Object>();

	protected UnavailableSourceImpl() {}

	@SuppressWarnings("unchecked")
	public static <T> Source<T> make() {
		return (Source<T>) SINGLETON;
	}

	// MARK: LazyLoadSourceDelegate
	@Override
	public T loadObject() throws RuntimeException, UnavailableSourceObjectException {
		throw new UnavailableSourceObjectException();
	}

	@Override
	public String toString() {
		return "UnavailableLazyLoadSourceDelegateImpl []";
	}

}
