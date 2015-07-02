package com.dereekb.gae.model.crud.services;

import com.dereekb.gae.model.crud.services.components.CreateService;
import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.components.UpdateService;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Service that provides Create, Read, Update, and Delete functionality.
 *
 * Should be a thread-safe implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model type
 */
public interface CrudService<T extends UniqueModel>
        extends CreateService<T>, ReadService<T>, UpdateService<T>, DeleteService<T> {

}
