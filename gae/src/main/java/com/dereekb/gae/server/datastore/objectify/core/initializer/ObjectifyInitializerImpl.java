package com.dereekb.gae.server.datastore.objectify.core.initializer;

import java.util.List;

import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseImpl;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Implementation of {@link ObjectifyInitializer} that uses a map of
 * @author dereekb
 */
@Deprecated
public class ObjectifyInitializerImpl
        implements ObjectifyInitializer {

	private List<Class<?>> types;

	public ObjectifyInitializerImpl(List<Class<?>> types) {
		super();
		this.types = types;
	}

	@Override
	public void initializeService(ObjectifyDatabaseImpl service) {
		ObjectifyFactory factory = ObjectifyService.factory();

		for (Class<?> type : this.types) {
			factory.register(type);
		}

	}

	public List<Class<?>> getTypes() {
		return this.types;
	}

	public void setTypes(List<Class<?>> types) {
		this.types = types;
	}

}
