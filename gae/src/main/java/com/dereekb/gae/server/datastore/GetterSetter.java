package com.dereekb.gae.server.datastore;

import com.dereekb.gae.server.datastore.models.UniqueModel;

public interface GetterSetter<T extends UniqueModel>
        extends Getter<T>, Setter<T> {

}
