package com.dereekb.gae.web.api.auth.controller.model.roles;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ApiModelRolesRequest;
import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ApiModelRolesResponseData;
import com.dereekb.gae.web.api.auth.exception.ApiLoginException;
import com.dereekb.gae.web.api.exception.ApiCaughtRuntimeException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.model.exception.resolver.AtomicOperationFailureResolver;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * API controller for accessing model roles.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/model")
public class ModelRolesController {

	private ModelRolesControllerDelegate delegate;

	public ModelRolesController(ModelRolesControllerDelegate delegate) {
		this.setDelegate(delegate);
	}

	public ModelRolesControllerDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(ModelRolesControllerDelegate delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	// MARK: API
	@ResponseBody
	@RequestMapping(path = "/roles", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public final ApiResponse readRolesForModels(@Valid @RequestBody ApiModelRolesRequest request)
	        throws ApiLoginException,
	            ApiCaughtRuntimeException {
		ApiResponseImpl response = null;

		try {
			response = new ApiResponseImpl();

			ApiModelRolesResponseData rolesResponse = this.delegate.readRoles(request);
			response.setData(rolesResponse);

			// TODO: Add missing keys as an error.

		} catch (AtomicOperationException e) {
			AtomicOperationFailureResolver.resolve(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

}
