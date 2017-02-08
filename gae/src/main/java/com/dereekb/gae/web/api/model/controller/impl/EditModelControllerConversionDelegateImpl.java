package com.dereekb.gae.web.api.model.controller.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.request.impl.CreateRequestImpl;
import com.dereekb.gae.model.crud.services.request.impl.DeleteRequestImpl;
import com.dereekb.gae.model.crud.services.request.impl.UpdateRequestImpl;
import com.dereekb.gae.model.crud.services.request.options.CreateRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.DeleteRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.UpdateRequestOptions;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.model.crud.services.response.DeleteResponse;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.model.controller.EditModelController;
import com.dereekb.gae.web.api.model.controller.EditModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.request.ApiCreateRequest;
import com.dereekb.gae.web.api.model.request.ApiDeleteRequest;
import com.dereekb.gae.web.api.model.request.ApiUpdateRequest;
import com.dereekb.gae.web.api.shared.exception.RequestArgumentException;
import com.dereekb.gae.web.api.shared.request.ApiRequest;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseDataImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * Default implementation of {@link EditModelControllerConversionDelegate}.
 *
 * @author dereekb
 *
 * @param <T>
 * @param <I>
 * @see {@link EditModelController}
 */
public final class EditModelControllerConversionDelegateImpl<T extends UniqueModel, I>
        implements EditModelControllerConversionDelegate<T, I> {

	private final String type;

	private final DirectionalConverter<String, ModelKey> keyReader;
	private final BidirectionalConverter<T, I> converter;

	public EditModelControllerConversionDelegateImpl(String type,
	        DirectionalConverter<String, ModelKey> keyReader,
	        BidirectionalConverter<T, I> converter) {
		this.type = type;
		this.keyReader = keyReader;
		this.converter = converter;
	}

	@Override
	public CreateRequest<T> convert(ApiCreateRequest<I> request) throws RequestArgumentException {
		List<T> templates = this.convertRequestTemplateData(request);
		CreateRequestOptions options = request.getOptions();
		CreateRequestImpl<T> serviceRequest = new CreateRequestImpl<T>(templates, options);
		return serviceRequest;
	}

	@Override
	public UpdateRequest<T> convert(ApiUpdateRequest<I> request) throws RequestArgumentException {
		List<T> templates = this.convertRequestTemplateData(request);
		UpdateRequestOptions options = request.getOptions();
		UpdateRequestImpl<T> serviceRequest = new UpdateRequestImpl<T>(templates, options);
		return serviceRequest;
	}

	private List<T> convertRequestTemplateData(ApiRequest<I> request) throws RequestArgumentException {
		List<I> input = request.getData();

		if (input == null || input.isEmpty()) {
			throw new RequestArgumentException("No Template Data", "The request was missing required model data.");
		}

		return this.converter.convertFrom(input);
	}

	@Override
	public DeleteRequest convert(ApiDeleteRequest request) throws RequestArgumentException {

		List<String> data = request.getData();

		List<ModelKey> keys = null;
		DeleteRequestOptions options = request.getOptions();

		try {
			keys = this.keyReader.convert(data);
		} catch (ConversionFailureException e) {
			throw new RequestArgumentException("data", "Failed to convert identifiers from data.");
		}

		DeleteRequestImpl serviceRequest = new DeleteRequestImpl(keys, options);
		return serviceRequest;
	}

	@Override
	public ApiResponse convert(CreateResponse<T> response) {

		Collection<T> created = response.getCreatedModels();
		List<I> converted = this.converter.convertTo(created);

		ApiResponseImpl apiResponse = new ApiResponseImpl();
		ApiResponseDataImpl data = new ApiResponseDataImpl(this.type, converted);

		apiResponse.setData(data);

		return apiResponse;
	}

	@Override
	public ApiResponse convert(UpdateResponse<T> response) {

		Collection<T> updated = response.getUpdatedModels();
		List<I> converted = this.converter.convertTo(updated);

		ApiResponseImpl apiResponse = new ApiResponseImpl();
		ApiResponseDataImpl data = new ApiResponseDataImpl(this.type, converted);

		apiResponse.setData(data);

		return apiResponse;
	}

	@Override
	public ApiResponse convert(DeleteResponse<T> response) {
		return this.convert(response, true);
	}

	@Override
	public ApiResponse convert(DeleteResponse<T> response,
	                           boolean includeModels) {
		Collection<T> deleted = response.getDeletedModels();

		ApiResponseImpl apiResponse = new ApiResponseImpl();

		ApiResponseDataImpl data = null;

		if (includeModels) {
			List<I> converted = this.converter.convertTo(deleted);
			data = new ApiResponseDataImpl(this.type, converted);
		} else {
			List<ModelKey> keys = ModelKey.readModelKeys(deleted);
			data = new ApiResponseDataImpl(this.type, keys);
		}

		apiResponse.setData(data);
		return apiResponse;
	}

}
