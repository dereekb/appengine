package com.dereekb.gae.utilities.model.lazy.impl;

import com.dereekb.gae.utilities.model.lazy.LazyLoadSource;
import com.dereekb.gae.utilities.model.source.impl.UnavailableSourceImpl;

/**
 * {@link LazyLoadSource} implementation that never is available.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class UnavailableLazyLoadSourceImpl<T> extends UnavailableSourceImpl<T>
        implements LazyLoadSource<T> {

	private static final LazyLoadSource<?> SINGLETON = new UnavailableLazyLoadSourceImpl<Object>();

	protected UnavailableLazyLoadSourceImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	public static <T> LazyLoadSource<T> make() {
		return (LazyLoadSource<T>) SINGLETON;
	}

	// MARK: LazyLoadSource
	@Override
	public boolean hasTriedLoading() {
		return true;
	}

	@Override
	public boolean hasModel() {
		return false;
	}

	@Override
	public T safeLoadObject() throws RuntimeException {
		return null;
	}

	@Override
	public String toString() {
		return "UnavailableLazyLoadSourceImpl []";
	}

}
