package com.dereekb.gae.utilities.factory.impl;

import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.model.source.Source;

/**
 * {@link Factory} implementation that uses a {@link Source}.
 *
 * @author dereekb
 *
 */
public class SourceFactoryImpl<T>
        implements Factory<T> {

	private Source<T> source;

	protected SourceFactoryImpl() {}

	public SourceFactoryImpl(Source<T> source) {
		this.setSource(source);
	}

	public Source<T> getSource() {
		return this.source;
	}

	public void setSource(Source<T> source) {
		if (source == null) {
			throw new IllegalArgumentException("source cannot be null.");
		}

		this.source = source;
	}

	// MARK: Factory
	@Override
	public T make() throws FactoryMakeFailureException {
		try {
			return this.source.loadObject();
		} catch (RuntimeException e) {
			throw new FactoryMakeFailureException(e);
		}
	}

	@Override
	public String toString() {
		return "SourceFactoryImpl [source=" + this.source + "]";
	}

}
