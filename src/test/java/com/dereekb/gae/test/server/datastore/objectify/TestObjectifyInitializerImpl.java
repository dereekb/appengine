package com.dereekb.gae.test.server.datastore.objectify;

import java.io.IOException;

import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyInitializerImpl;
import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;
import com.dereekb.gae.utilities.model.source.Source;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.testing.LocalDatastoreHelper;

/**
 * {@link ObjectifyInitializerImpl} extension for local unit testing.
 *
 * @author dereekb
 *
 */
public class TestObjectifyInitializerImpl extends ObjectifyInitializerImpl {

	private final double consistency = 1.0d;
	private final LocalDatastoreHelper datastoreHelper = LocalDatastoreHelper.create(this.consistency);

	public TestObjectifyInitializerImpl() {
		super.setDatastoreSource(new TestDatabaseSource());
	}

	@Override
	public void reset() {
		super.reset();

		try {
			this.datastoreHelper.reset();
		} catch (IOException e) {
			e.printStackTrace();
			// Do nothing else.
		}
	}

	private class TestDatabaseSource implements Source<Datastore> {

		@Override
		public Datastore loadObject() throws RuntimeException, UnavailableSourceObjectException {
			return TestObjectifyInitializerImpl.this.datastoreHelper.getOptions().getService();
		}

	}

}
