package com.dereekb.gae.web.api.model.crud.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.model.crud.services.response.DeleteResponse;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.objectify.helpers.ObjectifyTransactionUtility;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
import com.dereekb.gae.web.api.exception.ApiUnsupportedOperationException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.model.crud.request.ApiCreateRequest;
import com.dereekb.gae.web.api.model.crud.request.ApiDeleteRequest;
import com.dereekb.gae.web.api.model.crud.request.ApiUpdateRequest;
import com.dereekb.gae.web.api.model.exception.TooManyRequestKeysException;
import com.dereekb.gae.web.api.model.exception.TooManyTemplatesException;
import com.dereekb.gae.web.api.model.exception.resolver.AtomicOperationFailureResolver;
import com.dereekb.gae.web.api.shared.response.ApiResponse;

/**
 * Provides functionality for Create, Update, and Delete of a certain type.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model Type
 * @param <I>
 *            Model Data Transfer Type
 */
public abstract class EditModelController<T extends UniqueModel, I> {

	public static final String ATOMIC_PARAM = ReadController.ATOMIC_PARAM;
	public static final String KEYS_PARAM = ReadController.KEYS_PARAM;
	public static final String RETURN_MODELS_PARAM = "returnModels";

	public static final Integer MAX_ATOMIC_EDIT_SIZE = ObjectifyTransactionUtility.MAX_TRANSACTION_ELEMENTS;

	private EditModelControllerDelegate<T> delegate;
	private EditModelControllerConversionDelegate<T, I> conversionDelegate;

	public EditModelController(EditModelControllerDelegate<T> delegate,
	        EditModelControllerConversionDelegate<T, I> conversionDelegate) throws IllegalArgumentException {
		this.setDelegate(delegate);
		this.setConversionDelegate(conversionDelegate);
	}

	public EditModelControllerDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(EditModelControllerDelegate<T> delegate) throws IllegalArgumentException {
		if (delegate == null) {
			throw new IllegalArgumentException();
		}

		this.delegate = delegate;
	}

	public EditModelControllerConversionDelegate<T, I> getConversionDelegate() {
		return this.conversionDelegate;
	}

	public void setConversionDelegate(EditModelControllerConversionDelegate<T, I> conversionDelegate)
	        throws IllegalArgumentException {
		if (conversionDelegate == null) {
			throw new IllegalArgumentException();
		}

		this.conversionDelegate = conversionDelegate;
	}

	// MARK: API
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ApiResponse create(@Valid @RequestBody ApiCreateRequest<I> request) {
		ApiResponse response = null;

		TooManyTemplatesException.assertTemplatesCount(request.getData(), MAX_ATOMIC_EDIT_SIZE);

		try {
			CreateRequest<T> createRequest = this.conversionDelegate.convert(request);
			CreateResponse<T> createResponse = this.delegate.create(createRequest);
			response = this.conversionDelegate.convert(createResponse);
		} catch (AtomicOperationException e) {
			AtomicOperationFailureResolver.resolve(e);
		} catch (IllegalArgumentException e) {
			throw new ApiIllegalArgumentException(e);
		} catch (UnsupportedOperationException e) {
			throw new ApiUnsupportedOperationException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	@ResponseBody
	@RequestMapping(value = { "/update",
	        "/edit" }, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ApiResponse update(@Valid @RequestBody ApiUpdateRequest<I> request) {
		ApiResponse response = null;

		TooManyTemplatesException.assertTemplatesCount(request.getData(), MAX_ATOMIC_EDIT_SIZE);

		try {
			UpdateRequest<T> updateRequest = this.conversionDelegate.convert(request);
			UpdateResponse<T> updateResponse = this.delegate.update(updateRequest);
			response = this.conversionDelegate.convert(updateResponse);
		} catch (AtomicOperationException e) {
			AtomicOperationFailureResolver.resolve(e);
		} catch (IllegalArgumentException e) {
			throw new ApiIllegalArgumentException(e);
		} catch (UnsupportedOperationException e) {
			throw new ApiUnsupportedOperationException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	@ResponseBody
	@RequestMapping(value = { "/delete", "/destroy" }, method = RequestMethod.DELETE, produces = "application/json")
	public ApiResponse delete(@RequestParam(name = EditModelController.KEYS_PARAM, required = true) List<String> keys,
                              @RequestParam(name = EditModelController.ATOMIC_PARAM, required = false, defaultValue = "false") boolean atomic,
                              @RequestParam(name = EditModelController.RETURN_MODELS_PARAM, required = false, defaultValue = "false") boolean returnModels) throws TooManyRequestKeysException {
		ApiDeleteRequest deleteRequest = new ApiDeleteRequest(keys, atomic);
		deleteRequest.setReturnModels(returnModels);
		return this.delete(deleteRequest);
	}

	private ApiResponse delete(ApiDeleteRequest request) throws TooManyRequestKeysException {
		ApiResponse response = null;

		List<String> keys = request.getData();
		TooManyRequestKeysException.assertKeysCount(keys, MAX_ATOMIC_EDIT_SIZE);

		try {
			DeleteRequest deleteRequest = this.conversionDelegate.convert(request);
			DeleteResponse<T> deleteResponse = this.delegate.delete(deleteRequest);
			response = this.conversionDelegate.convert(deleteResponse, request.getReturnModels());
		} catch (AtomicOperationException e) {
			AtomicOperationFailureResolver.resolve(e);
		} catch (IllegalArgumentException e) {
			throw new ApiIllegalArgumentException(e);
		} catch (UnsupportedOperationException e) {
			throw new ApiUnsupportedOperationException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

}
