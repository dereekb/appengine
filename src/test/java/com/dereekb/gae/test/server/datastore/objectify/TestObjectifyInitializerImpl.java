package com.dereekb.gae.test.server.datastore.objectify;

import java.io.IOException;

import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyInitializerImpl;
import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;
import com.dereekb.gae.utilities.model.source.Source;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.testing.LocalDatastoreHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;

/**
 * {@link ObjectifyInitializerImpl} extension for local unit testing.
 *
 * @author dereekb
 *
 */
public class TestObjectifyInitializerImpl extends ObjectifyInitializerImpl {

	private final double consistency = 1.0d;
	private final LocalDatastoreHelper datastoreHelper = LocalDatastoreHelper.create(this.consistency);
	private boolean started = false;

	private Closeable session;

	public TestObjectifyInitializerImpl() {
		super.setDatastoreSource(new TestDatabaseSource());
	}

	// MARK: Testing
	public void begin() {
		this.start();

		if (this.session == null) {
			this.session = ObjectifyService.begin();
		}
	}

	public void close() {
		if (this.session != null) {
			this.session.close();
			this.session = null;
		}
	}

	// MARK: Initialization
	public void start() {
		if (!this.started) {
			try {
				this.datastoreHelper.start();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

			this.started = true;
		}
	}

	@Override
	public void reset() {
		super.reset();
		this.close();

		try {
			this.datastoreHelper.reset();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private class TestDatabaseSource implements Source<Datastore> {

		@Override
		public Datastore loadObject() throws RuntimeException, UnavailableSourceObjectException {
			start();
			return TestObjectifyInitializerImpl.this.datastoreHelper.getOptions().getService();
		}

	}

}
