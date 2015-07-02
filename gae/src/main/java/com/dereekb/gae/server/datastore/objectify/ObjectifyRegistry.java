package com.dereekb.gae.server.datastore.objectify;

import com.dereekb.gae.server.datastore.Deleter;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedGetter;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedQuery;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedSetter;

public interface ObjectifyRegistry<T extends ObjectifyModel<T>>
        extends ObjectifyKeyedGetter<T>, ObjectifyKeyedSetter<T>, ObjectifyKeyedQuery<T>, GetterSetter<T>, Deleter {

}
