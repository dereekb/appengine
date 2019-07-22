package com.dereekb.gae.utilities.factory.impl;

import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;

/**
 * {@link Factory} that only returns {@code null} values.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class NullFactoryImpl<T>
        implements Factory<T> {

	// MARK: Factory
	@Override
	public T make() throws FactoryMakeFailureException {
		return null;
	}

}
