package com.dereekb.gae.model.extension.links.components.model.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;


/**
 * Delegate for {@link LinkModelSetImpl}.
 *
 * @author dereekb
 *
 */
public interface LinkModelSetImplDelegate<T> {

	public ReadResponse<T> readModels(Collection<ModelKey> keys);

	public void saveModels(List<T> models);

}
