package com.dereekb.gae.test.server.datastore.objectify;

import java.util.List;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntity;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityDefinition;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityModifier;
import com.dereekb.gae.server.datastore.objectify.core.exception.UnregisteredEntryTypeException;
import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseImpl;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;

/**
 * Extension of {@link ObjectifyDatabaseImpl} used for testing.
 *
 * @author dereekb
 *
 */
public class ObjectifyTestDatabase extends ObjectifyDatabaseImpl {

	private Boolean asyncOverride;

	public ObjectifyTestDatabase(Iterable<ObjectifyDatabaseEntityDefinition> entities) {
		super(entities);
	}

	public ObjectifyTestDatabase(Iterable<ObjectifyDatabaseEntityDefinition> entities, Boolean asyncOverride) {
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

	@Override
	public <T extends ObjectifyModel<T>> ObjectifyDatabaseEntity<T> getDatabaseEntity(Class<T> type) {
		return new TestObjectifyDatabaseEntityImpl<T>(type);
	}

	@Override
	protected Objectify ofy() {
		return ObjectifyService.ofy().cache(false);
	}

	protected class TestObjectifyDatabaseEntityImpl<T extends ObjectifyModel<T>> extends ObjectifyDatabaseEntityImpl<T> {

		protected TestObjectifyDatabaseEntityImpl(Class<T> type) throws UnregisteredEntryTypeException {
			super(type);
		}

		@Override
		public boolean isConfiguredAsync() {
			return ObjectifyTestDatabase.this.isAsync(super.isConfiguredAsync());
		}

		// MARK: ObjectifyDatabaseEntityModifier
		@Override
		public ObjectifyDatabaseEntityModifier<T> getModifier(boolean async) {
			async = ObjectifyTestDatabase.this.isAsync(async);
			return new ObjectifyDatabaseEntityModifierImpl(async);
		}

		// MARK: Reader
		@Override
		public T get(Key<T> key) {
			resync();
			return super.get(key);
		}

		@Override
		public List<T> keysGet(Iterable<Key<T>> list) {
			resync();
			return super.keysGet(list);
		}

		@Override
		public List<T> refsGet(Iterable<Ref<T>> list) {
			resync();
			return super.refsGet(list);
		}

	}

	// MARK: Internal
	public static void resync() {
		Objectify objectify = ObjectifyService.ofy();
		objectify.flush();
		objectify.clear();
	}

}
