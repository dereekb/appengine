package com.dereekb.gae.web.api.model.crud.controller;

import com.dereekb.gae.model.crud.services.EditService;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Delegate for {@link EditModelController} that implements
 * {@link EditService}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface EditModelControllerDelegate<T extends UniqueModel>
        extends EditService<T> {

}
