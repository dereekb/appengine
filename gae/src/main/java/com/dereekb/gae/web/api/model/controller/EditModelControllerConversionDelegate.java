package com.dereekb.gae.web.api.model.controller;

import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.model.crud.services.response.DeleteResponse;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.web.api.model.request.ApiCreateRequest;
import com.dereekb.gae.web.api.model.request.ApiDeleteRequest;
import com.dereekb.gae.web.api.model.request.ApiUpdateRequest;
import com.dereekb.gae.web.api.shared.response.ApiResponse;

/**
 * Used for performing conversions.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <I>
 *            model dto type
 * 
 * @see EditModelController
 */
public interface EditModelControllerConversionDelegate<T extends UniqueModel, I> {

	public CreateRequest<T> convert(ApiCreateRequest<I> request);

	public UpdateRequest<T> convert(ApiUpdateRequest<I> request);

	public DeleteRequest convert(ApiDeleteRequest request);

	public ApiResponse convert(CreateResponse<T> response);

	public ApiResponse convert(UpdateResponse<T> response);

	public ApiResponse convert(DeleteResponse<T> response);

	public ApiResponse convert(DeleteResponse<T> response,
	                           boolean includeModels);

}
