package com.dereekb.gae.web.api.auth.controller.model;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.web.api.auth.controller.model.impl.ApiLoginTokenModelContextRequest;
import com.dereekb.gae.web.api.auth.exception.ApiLoginException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.exception.ApiCaughtRuntimeException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.model.exception.resolver.AtomicOperationFailureResolver;

/**
 * Controller for model contexts.
 * 
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/login/auth/model")
public class LoginTokenModelContextController {

	private LoginTokenModelContextControllerDelegate delegate;

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
	@ResponseBody
	@RequestMapping(path = "/token", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public final LoginTokenPair makeLoginTokenContext(@Valid @RequestBody ApiLoginTokenModelContextRequest request)
	        throws ApiLoginException,
	            ApiCaughtRuntimeException {
		LoginTokenPair response = null;

		try {
			response = this.delegate.loginWithContext(request);
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