package com.dereekb.gae.server.datastore.objectify;

import com.dereekb.gae.server.datastore.KeyDeleter;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessorFactory;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedGetter;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedSetter;
import com.dereekb.gae.server.datastore.objectify.components.query.ObjectifyQueryService;
import com.dereekb.gae.server.datastore.objectify.keys.ObjectifyKeyConverter;
import com.dereekb.gae.server.datastore.objectify.query.iterator.ObjectifyQueryIterableFactory;
import com.dereekb.gae.server.datastore.utility.ConfiguredDeleter;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;

/**
 * Objectify Registry that implements various components.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyRegistry<T extends ObjectifyModel<T>>
        extends ObjectifyQueryIterableFactory<T>, ObjectifyKeyedGetter<T>, ObjectifyKeyedSetter<T>,
        ObjectifyQueryService<T>, ModelKeyListAccessorFactory<T>, ConfiguredSetter<T>, ConfiguredDeleter,
        GetterSetter<T>, KeyDeleter {

	public ObjectifyKeyConverter<T, ModelKey> getObjectifyKeyConverter();

}
