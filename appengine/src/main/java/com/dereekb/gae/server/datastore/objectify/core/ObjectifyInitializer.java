package com.dereekb.gae.server.datastore.objectify.core;

import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;
import com.dereekb.gae.utilities.model.source.Source;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;

/**
 * {@link Source} for the {@link ObjectifyFactory} and responsible for initializing Objectify.
 *
 * @author dereekb
 *
 */
public interface ObjectifyInitializer extends Source<ObjectifyFactory> {

	/**
	 * Initializes Objectify if not yet initialized, then returns the {@link ObjectifyFactory}.
	 *
	 * {@inheritDoc}
	 */
	@Override
	public ObjectifyFactory loadObject() throws RuntimeException, UnavailableSourceObjectException;

	/**
	 * Initializes {@link Objectify}.
	 *
	 * @throws RuntimeException
	 */
	public void initialize() throws RuntimeException;

}
