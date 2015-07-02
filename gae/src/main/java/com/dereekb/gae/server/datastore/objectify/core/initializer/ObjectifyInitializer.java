package com.dereekb.gae.server.datastore.objectify.core.initializer;

import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;

/**
 * Delegate type used to initialize an ObjectifyService singleton.
 *
 * @author dereekb
 */
@Deprecated
public interface ObjectifyInitializer {

	public void initializeService(ObjectifyDatabase service);

}
