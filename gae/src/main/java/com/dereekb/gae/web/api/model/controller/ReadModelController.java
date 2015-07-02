package com.dereekb.gae.web.api.model.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Max;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.web.api.model.exception.ApiRuntimeException;
import com.dereekb.gae.web.api.model.exception.resolver.AtomicOperationFailureResolver;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;

public final class ReadModelController<T extends UniqueModel> {

	private final ReadModelControllerDelegate<T> delegate;
	private final ReadModelControllerConversionDelegate<T> conversionDelegate;

	public ReadModelController(ReadModelControllerDelegate<T> delegate,
	        ReadModelControllerConversionDelegate<T> conversionDelegate) {
		this.delegate = delegate;
		this.conversionDelegate = conversionDelegate;
	}

	public ReadModelControllerDelegate<T> getDelegate() {
		return this.delegate;
	}

	public ReadModelControllerConversionDelegate<T> getConversionDelegate() {
		return this.conversionDelegate;
	}

	@ResponseBody
	@PreAuthorize("hasPermission(this, 'read')")
	@RequestMapping(value = "/read", method = RequestMethod.GET, produces = "application/json")
	public final ApiResponse read(@Max(40) @RequestParam(required = true) List<String> ids,
	                              @RequestParam(required = false, defaultValue = "false") boolean atomic,
	                              @RequestParam(required = false, defaultValue = "false") boolean getRelated) {
		ApiResponse response = null;

		try {
			ReadRequest<T> readRequest = this.conversionDelegate.convert(ids);
			ReadRequestOptions options = readRequest.getOptions();
			options.setAtomic(atomic);

			ReadResponse<T> readResponse = this.delegate.read(readRequest);
			response = this.conversionDelegate.convert(readResponse);

			if (getRelated) {
				Collection<T> models = readResponse.getModels();
				Map<String, Object> included = this.delegate.readIncluded(models);

				for (String type : included.keySet()) {
					Object data = included.get(type);
					ApiResponseData responseData = new ApiResponseData(type, data);
					response.addIncluded(responseData);
				}
			}
		} catch (AtomicOperationException e) {
			AtomicOperationFailureResolver.resolve(e);
		} catch (RuntimeException e) {
			throw new ApiRuntimeException(e);
		}

		return response;
	}

}
