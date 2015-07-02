package com.dereekb.gae.web.api.model.controller;

import java.util.Collection;
import java.util.Map;

import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;


public interface ReadModelControllerDelegate<T extends UniqueModel> {

	public ReadResponse<T> read(ReadRequest<T> readRequest);

	public Map<String, Object> readIncluded(Collection<T> models);

}
