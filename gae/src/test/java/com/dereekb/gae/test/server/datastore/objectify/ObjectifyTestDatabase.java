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
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;

/**
 * Extension of {@link ObjectifyDatabaseImpl} used for testing.
 *
 * @author dereekb
 *
 */
public class ObjectifyTestDatabase extends ObjectifyDatabaseImpl {

	public ObjectifyTestDatabase(Iterable<ObjectifyDatabaseEntityDefinition> entities) {
		super(entities);
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
		}

		@Override
		protected void resetReaderWriter() {
			this.setReader(new TestObjectifyDatabaseEntityReader());
			this.writer = new ObjectifyDatabaseEntityWriterImpl();
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

			@Override
			public List<T> refsGet(Iterable<Ref<T>> list) {
				resync();
				return super.refsGet(list);
			}

			@Override
			public Query<T> makeQuery(boolean allowCache) {
				resync();
				return super.makeQuery(allowCache);
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
