package com.dereekb.gae.server.datastore.objectify.core;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;

/**
 * Objectify Database. Used for retrieving {@link ObjectifyDatabaseEntity}
 * values.
 *
 * @author dereekb
 *
 */
public interface ObjectifyDatabase
        extends ObjectifySource {

	public <T extends ObjectifyModel<T>> ObjectifyDatabaseEntity<T> getDatabaseEntity(Class<T> type);

}
