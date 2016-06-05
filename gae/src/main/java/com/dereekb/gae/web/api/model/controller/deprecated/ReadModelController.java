package com.dereekb.gae.web.api.model.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.Max;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.web.api.model.exception.ApiRuntimeException;
import com.dereekb.gae.web.api.model.exception.resolver.AtomicOperationFailureResolver;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseDataImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * API Controller for reading models from the database.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
@Deprecated
public abstract class ReadModelController<T extends UniqueModel> {

	private ReadModelControllerDelegate<T> delegate;
	private ReadModelControllerConversionDelegate<T> conversionDelegate;

	public ReadModelController(ReadModelControllerDelegate<T> delegate,
	        ReadModelControllerConversionDelegate<T> conversionDelegate) {
		this.setDelegate(delegate);
		this.setConversionDelegate(conversionDelegate);
	}

	public ReadModelControllerDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(ReadModelControllerDelegate<T> delegate) {
		this.delegate = delegate;
	}

	public ReadModelControllerConversionDelegate<T> getConversionDelegate() {
		return this.conversionDelegate;
	}

	public void setConversionDelegate(ReadModelControllerConversionDelegate<T> conversionDelegate) {
		this.conversionDelegate = conversionDelegate;
	}

	/**
	 * API Entry point for reading a value.
	 *
	 * @param ids
	 *            Identifiers to read. Max of 40. Never {@code null}.
	 * @param atomic
	 *            Whether or not to perform an atomic read request. False by
	 *            default.
	 * @param getRelated
	 *            Whether or not to retrieve related values. False by default.
	 *            If {@code related} is not empty or {@code null}, this value is
	 *            set to {@code true}.
	 * @param related
	 *            Inclusive filter of related elements to load.
	 * @return {@link ApiResponse}
	 */
	@ResponseBody
	@PreAuthorize("hasPermission(this, 'read')")
	@RequestMapping(value = "/read", method = RequestMethod.GET, produces = "application/json")
	public final ApiResponse read(@Max(40) @RequestParam(required = true) List<String> ids,
	                              @RequestParam(required = false, defaultValue = "false") boolean atomic,
	                              @RequestParam(required = false, defaultValue = "false") boolean getRelated,
	                              @RequestParam(required = false) Set<String> related) {
		ApiResponseImpl response = null;

		try {
			ReadRequest readRequest = this.conversionDelegate.convert(ids);
			ReadRequestOptions options = readRequest.getOptions();
			options.setAtomic(atomic);

			ReadResponse<T> readResponse = this.delegate.read(readRequest);
			response = this.conversionDelegate.convert(readResponse);

			if (getRelated || (related != null && related.isEmpty() == false)) {
				Collection<T> models = readResponse.getModels();
				Map<String, Object> included = this.delegate.readIncluded(models, related);

				for (String type : included.keySet()) {
					Object data = included.get(type);
					ApiResponseDataImpl responseData = new ApiResponseDataImpl(type, data);
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