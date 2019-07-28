package com.dereekb.gae.web.api.auth.controller.model.context;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.web.api.auth.controller.model.context.impl.ApiLoginTokenModelContextRequest;
import com.dereekb.gae.web.api.auth.controller.model.context.impl.ApiLoginTokenModelContextResponse;
import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ApiModelRolesRequest;
import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ApiModelRolesResponseData;
import com.dereekb.gae.web.api.auth.exception.ApiLoginException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.exception.ApiCaughtRuntimeException;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.model.exception.resolver.AtomicOperationFailureResolver;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseDataImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * Controller for model contexts.
 *
 * @author dereekb
 *
 * @deprecated Design does not work for microservice systems where the login
 *             server does not have contextual knowledge to satisfy all
 *             requests, and other servers are not allowed to generated
 *             LoginToken values of their own.
 */
@RestController
@RequestMapping("/login/auth/model")
@Deprecated
public class LoginTokenModelContextController {

	private LoginTokenModelContextControllerDelegate delegate;

	public LoginTokenModelContextController(LoginTokenModelContextControllerDelegate delegate) {
		this.setDelegate(delegate);
	}

	public LoginTokenModelContextControllerDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(LoginTokenModelContextControllerDelegate delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	// MARK: API
	/**
	 * Primary request that can build a new {@link LoginToken} context from the
	 * requested models and request security context.
	 *
	 * @param request
	 *            {@link ApiLoginTokenModelContextRequest}. Never {@code null}.
	 * @return {@link ApiResponse}. Never {@code null}.
	 */
	@ResponseBody
	@RequestMapping(path = "/token", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public final ApiResponse makeLoginTokenContext(@Valid @RequestBody ApiLoginTokenModelContextRequest request)
	        throws ApiLoginException,
	            ApiCaughtRuntimeException {
		ApiResponseImpl response = null;

		try {
			response = new ApiResponseImpl();

			ApiLoginTokenModelContextResponse delegateResponse = this.delegate.loginWithContext(request);
			LoginTokenPair pair = delegateResponse.getLoginToken();

			if (pair != null) {
				ApiResponseData pairData = new ApiResponseDataImpl(LoginTokenPair.DATA_TYPE, pair);
				response.setData(pairData);
			}

			ApiModelRolesResponseData rolesResponse = delegateResponse.getRolesData();

			if (rolesResponse != null) {
				response.addIncluded(rolesResponse);
			}

			// TODO: Add missing keys as an error.

		} catch (AtomicOperationException e) {
			AtomicOperationFailureResolver.resolve(e);
		} catch (IllegalArgumentException e) {
			throw new ApiIllegalArgumentException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	/**
	 *
	 * @param request
	 * @return
	 * @throws ApiLoginException
	 * @throws ApiCaughtRuntimeException
	 *
	 * @deprecated Better to just use
	 *             {@link #makeLoginTokenContext(ApiLoginTokenModelContextRequest)}
	 *             with the specific config instead.
	 */
	@Deprecated
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

	@Override
	public String toString() {
		return "LoginTokenModelContextController [delegate=" + this.delegate + "]";
	}

}
