package com.dereekb.gae.server.datastore.objectify.core;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedGetter;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedSetter;

/**
 * ObjectifyDatabaseEntity that provides additional functions atop of
 * {@link ObjectifyRegistry}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyDatabaseEntity<T extends ObjectifyModel<T>>
        extends ObjectifyRegistry<T> {

	public ObjectifyDatabaseEntityKeyEnforcement getKeyEnforcement();
	
	public ObjectifyKeyedGetter<T> getter();

	public ObjectifyKeyedSetter<T> setter();

	public ObjectifyDatabaseEntityReader<T> getReader();

	public ObjectifyDatabaseEntityWriter<T> getWriter();

}
