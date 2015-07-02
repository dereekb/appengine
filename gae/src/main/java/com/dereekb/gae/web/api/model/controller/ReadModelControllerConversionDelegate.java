package com.dereekb.gae.web.api.model.controller;

import java.util.List;

import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.web.api.shared.response.ApiResponse;

public interface ReadModelControllerConversionDelegate<T extends UniqueModel> {

	public ReadRequest<T> convert(List<String> ids);

	public ApiResponse convert(ReadResponse<T> response);

}
