package com.dereekb.gae.web.api.model.crud.controller;

import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.model.crud.services.response.DeleteResponse;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.web.api.model.crud.exception.NoTemplateDataExeption;
import com.dereekb.gae.web.api.model.crud.request.ApiCreateRequest;
import com.dereekb.gae.web.api.model.crud.request.ApiDeleteRequest;
import com.dereekb.gae.web.api.model.crud.request.ApiUpdateRequest;
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

	// MARK: Requests
	public CreateRequest<T> convert(ApiCreateRequest<I> request) throws NoTemplateDataExeption;

	public UpdateRequest<T> convert(ApiUpdateRequest<I> request) throws NoTemplateDataExeption;

	public DeleteRequest convert(ApiDeleteRequest request) throws ConversionFailureException;

	// MARK: Responses
	public ApiResponse convert(CreateResponse<T> response);

	public ApiResponse convert(UpdateResponse<T> response);

	public ApiResponse convert(DeleteResponse<T> response);

	public ApiResponse convert(DeleteResponse<T> response,
	                           boolean includeModels);

}
