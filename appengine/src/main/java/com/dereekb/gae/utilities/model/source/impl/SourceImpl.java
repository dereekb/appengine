package com.dereekb.gae.utilities.model.source.impl;

import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;
import com.dereekb.gae.utilities.model.source.Source;

/**
 * {@link Source} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class SourceImpl<T>
        implements Source<T> {

	private T object;

	public SourceImpl(T object) {
		super();
		this.setObject(object);
	}

	public static <T> Source<T> nullSource() {
		return new SourceImpl<T>(null);
	}

	public static <T> Source<T> make(T object) {
		return new SourceImpl<T>(object);
	}

	public T getObject() {
		return this.object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	// MARK: Source
	@Override
	public T loadObject() throws RuntimeException {
		return this.getObject();
	}

	public static <T> T safeLoad(Source<T> object) throws RuntimeException {
		try {
			return object.loadObject();
		} catch (UnavailableSourceObjectException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return "SourceImpl [object=" + this.object + "]";
	}

}
