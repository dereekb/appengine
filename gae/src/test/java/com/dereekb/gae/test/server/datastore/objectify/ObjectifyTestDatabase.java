package com.dereekb.gae.test.server.datastore.objectify;

import java.util.logging.Logger;

import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityDefinition;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;


public class ObjectifyTestDatabase extends ObjectifyDatabase {

	private Boolean asyncOverride;
	private Logger logger = Logger.getLogger("ObjectifyTestDatabase");

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
		this.logger.info("Deleting Entity: " + entity);
		super.delete(entity, this.isAsync(async));
	}

	@Override
	public <T> void delete(Key<T> key,
	                       boolean async) {
		this.logger.info("Deleting Key: " + key);
		super.delete(key, this.isAsync(async));
	}

	@Override
	public <T> void delete(Ref<T> ref,
	                       boolean async) {
		this.logger.info("Deleting Ref: " + ref);
		super.delete(ref, this.isAsync(async));
	}

	@Override
	public <T> void delete(Iterable<T> list,
	                       boolean async) {
		this.logger.info("Deleting List: " + list);
		super.delete(list, this.isAsync(async));
	}

	@Override
	public <T> void deleteWithKeys(Iterable<Key<T>> list,
	                               boolean async) {
		this.logger.info("Deleting List of Keys: " + list);
		super.deleteWithKeys(list, this.isAsync(async));
	}

}
