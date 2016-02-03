package com.dereekb.gae.server.datastore.objectify.core;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;

/**
 * Used for retrieving and building objectify readers and modifiers.
 * <p>
 * Extends {@link ObjectifyRegistry}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyDatabaseEntity<T extends ObjectifyModel<T>>
        extends ObjectifyRegistry<T> {

	public ObjectifyDatabaseEntityReader<T> getReader();

	public ObjectifyDatabaseEntityModifier<T> getModifier(boolean async);

}
