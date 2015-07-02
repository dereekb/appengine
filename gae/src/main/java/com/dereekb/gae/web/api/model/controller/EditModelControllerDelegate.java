package com.dereekb.gae.web.api.model.controller;

import com.dereekb.gae.model.crud.services.components.CreateService;
import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.model.crud.services.components.UpdateService;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Delegate for {@link EditModelController} that implements the
 * {@link CreateService}, {@link UpdateService}, {@link DeleteService}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface EditModelControllerDelegate<T extends UniqueModel>
        extends CreateService<T>, UpdateService<T>, DeleteService<T> {

}
