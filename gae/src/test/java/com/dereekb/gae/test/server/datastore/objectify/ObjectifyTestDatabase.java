package com.dereekb.gae.test.server.datastore.objectify;

import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityDefinition;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;


public class ObjectifyTestDatabase extends ObjectifyDatabase {

	private Boolean asyncOverride;

	public ObjectifyTestDatabase(Iterable<ObjectifyDatabaseEntityDefinition<?>> entities) {
		super(entities);
	}

	public ObjectifyTestDatabase(Iterable<ObjectifyDatabaseEntityDefinition<?>> entities, Boolean asyncOverride) {
		super(entities);
		this.asyncOverride = asyncOverride;
	}

	public Boolean getAsyncOverride() {
		return this.asyncOverride;
	}

	public void setAsyncOverride(Boolean asyncOverride) {
		this.asyncOverride = asyncOverride;
	}

	private boolean isAsync(boolean value) {

		if (this.asyncOverride != null) {
			value = this.asyncOverride;
		}

		return value;
	}

	// Add/Put
	@Override
	public <T> void put(T entity,
	                    boolean async) {
		super.put(entity, this.isAsync(async));
	}

	@Override
	public <T> void put(Iterable<T> entities,
	                    boolean async) {
		super.put(entities, this.isAsync(async));
	}

	// Delete
	@Override
	public <T> void delete(T entity,
	                       boolean async) {
		super.delete(entity, this.isAsync(async));
	}

	@Override
	public <T> void delete(Key<T> key,
	                       boolean async) {
		super.delete(key, this.isAsync(async));
	}

	@Override
	public <T> void delete(Ref<T> ref,
	                       boolean async) {
		super.delete(ref, this.isAsync(async));
	}

	@Override
	public <T> void delete(Iterable<T> list,
	                       boolean async) {
		super.delete(list, this.isAsync(async));
	}

	@Override
	public <T> void deleteWithKeys(Iterable<Key<T>> list,
	                               boolean async) {
		super.deleteWithKeys(list, this.isAsync(async));
	}

}
