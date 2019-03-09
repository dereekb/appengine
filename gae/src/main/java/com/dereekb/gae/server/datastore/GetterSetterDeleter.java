package com.dereekb.gae.server.datastore;

import com.dereekb.gae.server.datastore.models.UniqueModel;


public interface GetterSetterDeleter<T extends UniqueModel>
        extends GetterSetter<T>, KeyDeleter {

}
