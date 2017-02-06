package com.dereekb.gae.server.datastore.objectify.components.impl;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedSetter;
import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseImpl;
import com.googlecode.objectify.Key;

/**
 * @author dereekb
 *
 * @param <T>
 * @see {@link ObjectifyModelRegistry}
 */
@Deprecated
public final class ObjectifyModelSetter<T extends ObjectifyModel<T>> extends ObjectifyModelDatastoreComponent<T>
        implements ObjectifyKeyedSetter<T> {

	public ObjectifyModelSetter(ObjectifyDatabaseImpl database, Class<T> type) {
		super(database, type);
	}

	// Put
	@Override
    public void save(T entity,
	                 boolean async) {
		this.database.put(entity, async);
	}

	@Override
    public void save(Iterable<T> entities,
	                 boolean async) {
		this.database.put(entities, async);
	}

	@Override
    public void delete(T entity,
	                   boolean now) {
		this.database.delete(entity, now);
	}

	@Override
	public void delete(Iterable<T> entities,
	                   boolean async) {
		this.database.delete(entities, async);
	}

	@Override
	public void delete(Key<T> entity,
	                   boolean async) {
		this.database.delete(entity, async);
	}

	@Override
	public void deleteWithObjectifyKeys(Iterable<Key<T>> entities,
	                           boolean async) {
		this.database.deleteWithKeys(entities, async);
	}

}
