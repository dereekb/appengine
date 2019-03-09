package com.dereekb.gae.server.datastore.objectify.core;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.utility.ConfiguredDeleter;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;

/**
 * Used for retrieving and building objectify readers and modifiers.
 * <p>
 * Extends {@link ObjectifyRegistry}, {@link ConfiguredSetter} and
 * {@link ConfiguredDeleter}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyDatabaseEntity<T extends ObjectifyModel<T>>
        extends ObjectifyRegistry<T>, ConfiguredSetter<T>, ConfiguredDeleter {

	public ObjectifyDatabaseEntityReader<T> getReader();

	public ObjectifyDatabaseEntityModifier<T> getModifier(boolean async);

}
