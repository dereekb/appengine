package com.dereekb.gae.test.server.datastore.objectify;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntity;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityDefinition;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityModifier;
import com.dereekb.gae.server.datastore.objectify.core.exception.UnregisteredEntryTypeException;
import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseImpl;

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

	private class TestObjectifyDatabaseEntityImpl<T extends ObjectifyModel<T>> extends ObjectifyDatabaseEntityImpl<T> {

		protected TestObjectifyDatabaseEntityImpl(Class<T> type) throws UnregisteredEntryTypeException {
			super(type);
		}

		// MARK: ObjectifyDatabaseEntityModifier
		@Override
		public ObjectifyDatabaseEntityModifier<T> getModifier(boolean async) {
			async = ObjectifyTestDatabase.this.isAsync(async);
			return new ObjectifyDatabaseEntityModifierImpl(async);
		}

	}

}
