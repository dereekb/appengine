package com.dereekb.gae.web.api.model.deprecated.controller;

import java.util.List;

import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * {@link ReadModelController} delegate use for performing conversions.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
@Deprecated
public interface ReadModelControllerConversionDelegate<T extends UniqueModel> {

	public ReadRequest convert(List<String> ids);

	public ApiResponseImpl convert(ReadResponse<T> response);

}
