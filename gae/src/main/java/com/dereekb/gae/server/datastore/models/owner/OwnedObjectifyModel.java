package com.dereekb.gae.server.datastore.models.owner;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;

public interface OwnedObjectifyModel<T>
        extends ObjectifyModel<T>, OwnedModel {

}
