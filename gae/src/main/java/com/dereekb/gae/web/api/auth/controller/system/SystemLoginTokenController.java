package com.dereekb.gae.web.api.auth.controller.system;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.web.api.auth.controller.system.impl.ApiSystemLoginTokenRequest;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;

/**
 * Controller for authenticating systems.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/login/auth/system")
public class SystemLoginTokenController {

	private SystemLoginTokenControllerDelegate delegate;

	public SystemLoginTokenController(SystemLoginTokenControllerDelegate delegate) {
		this.setDelegate(delegate);
	}

	public SystemLoginTokenControllerDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(SystemLoginTokenControllerDelegate delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	// MARK: System
	/**
	 * Creates an authentication token for the remote system.
	 */
	@ResponseBody
	@RequestMapping(value = "/token", method = RequestMethod.POST, produces = "application/json")
	public final LoginTokenPair makeSystemToken(@Valid @RequestBody ApiSystemLoginTokenRequest request)
	        throws NoSecurityContextException {
		LoginTokenPair result = null;

		try {
			result = this.delegate.makeSystemToken(request);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return result;
	}

	@Override
	public String toString() {
		return "SystemTokenController [delegate=" + this.delegate + "]";
	}

}
