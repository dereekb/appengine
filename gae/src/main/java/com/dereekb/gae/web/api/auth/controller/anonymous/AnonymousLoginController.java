package com.dereekb.gae.web.api.auth.controller.anonymous;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.model.exception.ApiRuntimeException;

/**
 * API used for creating an anonymous login token.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/login/auth/anon")
public final class AnonymousLoginController {

	private AnonymousLoginControllerDelegate delegate;

	public AnonymousLoginController(AnonymousLoginControllerDelegate delegate) {
		this.setDelegate(delegate);
	}

	public AnonymousLoginControllerDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(AnonymousLoginControllerDelegate delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException();
		}

		this.delegate = delegate;
	}

	// MARK: Controller
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public final LoginTokenPair login(@RequestParam(required = false) String anonymousId) {
		LoginTokenPair response = null;

		try {
			response = this.delegate.anonymousLogin(anonymousId);
		} catch (RuntimeException e) {
			throw new ApiRuntimeException(e);
		}

		return response;
	}

	@Override
	public String toString() {
		return "AnonymousLoginController [delegate=" + this.delegate + "]";
	}

}
