package com.dereekb.gae.server.datastore.objectify.components;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModelRegistry;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;
import com.googlecode.objectify.Key;

/**
 * @author dereekb
 *
 * @param <T>
 * @see {@link ObjectifyModelRegistry}
 */
public final class ObjectifyModelSetter<T extends ObjectifyModel<T>> extends ObjectifyModelDatastoreComponent<T>
        implements ObjectifyKeyedSetter<T> {

	public ObjectifyModelSetter(ObjectifyDatabase database, Class<T> type) {
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
