package com.dereekb.gae.server.datastore.objectify.core.impl;

import com.dereekb.gae.server.datastore.objectify.core.ObjectifyInitializer;
import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Abstract {@link ObjectifyInitializer} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractObjectifyInitializerImpl
        implements ObjectifyInitializer {

	private ObjectifyFactory objectifyFactory;

	// MARK: ObjectifyInitializer
	@Override
	public final void initialize() {
		this.loadObject();
	}

	@Override
	public final ObjectifyFactory loadObject() throws RuntimeException, UnavailableSourceObjectException {
		if (this.objectifyFactory == null) {
			this.objectifyFactory = this.buildFactory();
			this.initializeObjectify();
		}

		return this.objectifyFactory;
	}

	protected boolean isInitialized() {
		return this.objectifyFactory != null;
	}

	protected void initializeObjectify() {
		ObjectifyService.init(this.objectifyFactory);
	}

	protected abstract ObjectifyFactory buildFactory();

	protected void reset() {
		this.objectifyFactory = null;
	}

	@Override
	public String toString() {
		return "AbstractObjectifyInitializerImpl [objectifyFactory=" + this.objectifyFactory + "]";
	}

}
