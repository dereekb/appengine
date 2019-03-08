package com.dereekb.gae.server.datastore.objectify.core.impl;

import com.dereekb.gae.server.datastore.objectify.core.ObjectifyInitializer;
import com.dereekb.gae.utilities.model.source.Source;
import com.google.cloud.datastore.Datastore;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.cache.MemcacheService;

/**
 * {@link ObjectifyInitializer} implementation.
 *
 * @author dereekb
 *
 */
public class ObjectifyInitializerImpl extends AbstractObjectifyInitializerImpl {

	private Source<Datastore> datastoreSource;
	private Source<MemcacheService> memcacheSource;

	public ObjectifyInitializerImpl() {}

	public ObjectifyInitializerImpl(Source<MemcacheService> memcacheSource) {
		this(memcacheSource, null);
	}

	public ObjectifyInitializerImpl(Source<MemcacheService> memcacheSource, Source<Datastore> datastoreSource) {
		super();
		this.setMemcacheSource(memcacheSource);
		this.setDatastoreSource(datastoreSource);
	}

	public Source<Datastore> getDatastoreSource() {
		return this.datastoreSource;
	}

	public void setDatastoreSource(Source<Datastore> datastoreSource) {
		this.datastoreSource = datastoreSource;
	}

	public Source<MemcacheService> getMemcacheSource() {
		return this.memcacheSource;
	}

	public void setMemcacheSource(Source<MemcacheService> memcacheSource) {
		this.memcacheSource = memcacheSource;
	}

	// MARK: AbstractObjectifyInitializerImpl
	@Override
	protected ObjectifyFactory buildFactory() {

		MemcacheService service = null;

		if (this.memcacheSource != null) {
			service = this.memcacheSource.loadObject();
		}

		Datastore datastore = null;

		if (this.datastoreSource != null) {
			datastore = this.datastoreSource.loadObject();
		}

		ObjectifyFactory factory;

		if (datastore != null) {
			factory = new ObjectifyFactory(datastore, service);
		} else {
			factory = new ObjectifyFactory(service);
		}

		return factory;
	}

	@Override
	public String toString() {
		return "ObjectifyInitializerImpl [datastoreSource=" + this.datastoreSource + ", memcacheSource="
		        + this.memcacheSource + ", toString()=" + super.toString() + "]";
	}

}
