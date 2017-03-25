package com.dereekb.gae.test.server.datastore.objectify;

import java.util.List;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntity;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityDefinition;
import com.dereekb.gae.server.datastore.objectify.core.exception.UnregisteredEntryTypeException;
import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseImpl;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

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
	public Objectify ofy() {
		return super.ofy().cache(false);
	}

	protected class TestObjectifyDatabaseEntityImpl<T extends ObjectifyModel<T>> extends ObjectifyDatabaseEntityImpl<T> {

		protected TestObjectifyDatabaseEntityImpl(Class<T> type) throws UnregisteredEntryTypeException {
			super(type);
			this.reader = new TestObjectifyDatabaseEntityReader();
		}

		// MARK: Reader
		private class TestObjectifyDatabaseEntityReader extends ObjectifyDatabaseEntityReaderImpl {

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

		}

	}

	// MARK: Internal
	public static void resync() {
		Objectify objectify = ObjectifyService.ofy();
		objectify.flush();
		objectify.clear();
	}

}
