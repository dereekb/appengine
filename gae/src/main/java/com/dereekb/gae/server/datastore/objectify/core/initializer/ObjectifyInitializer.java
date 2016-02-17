package com.dereekb.gae.server.datastore.objectify.core.initializer;

import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseImpl;

/**
 * Delegate type used to initialize an ObjectifyService singleton.
 *
 * @author dereekb
 */
@Deprecated
public interface ObjectifyInitializer {

	public void initializeService(ObjectifyDatabaseImpl service);

}
