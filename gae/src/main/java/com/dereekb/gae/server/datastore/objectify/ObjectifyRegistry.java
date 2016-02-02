package com.dereekb.gae.server.datastore.objectify;

import com.dereekb.gae.server.datastore.Deleter;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessorFactory;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedGetter;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedSetter;
import com.dereekb.gae.server.datastore.objectify.components.query.ObjectifyQueryService;

public interface ObjectifyRegistry<T extends ObjectifyModel<T>>
        extends ObjectifyKeyedGetter<T>, ObjectifyKeyedSetter<T>, ObjectifyQueryService<T>,
        ModelKeyListAccessorFactory<T>, GetterSetter<T>, Deleter {

}
