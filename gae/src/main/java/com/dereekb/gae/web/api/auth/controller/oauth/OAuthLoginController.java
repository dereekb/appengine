package com.dereekb.gae.web.api.auth.controller.oauth;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthInsufficientException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthServiceUnavailableException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.model.exception.ApiRuntimeException;

@RestController
@RequestMapping("/login/oauth")
public class OAuthLoginController {

	private OAuthLoginControllerDelegate delegate;

	public OAuthLoginController(OAuthLoginControllerDelegate delegate) throws IllegalArgumentException {
		this.setDelegate(delegate);
	}

	public OAuthLoginControllerDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(OAuthLoginControllerDelegate delegate) throws IllegalArgumentException {
		if (delegate == null) {
			throw new IllegalArgumentException("Delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	// MARK: Controller
	@ResponseBody
	@RequestMapping(path = "/{type}", method = RequestMethod.POST, produces = "application/json")
	public final LoginTokenPair login(@PathVariable("type") String type,
	                                  @RequestParam @NotEmpty String accessToken) {
		LoginTokenPair response = null;

		try {
			response = this.delegate.login(type, accessToken);
		} catch (OAuthServiceUnavailableException e) {
			throw new ApiLoginException(ApiLoginException.LoginExceptionReason.UNSUPPORTED, e);
		} catch (OAuthInsufficientException e) {
			throw new ApiLoginException(ApiLoginException.LoginExceptionReason.INVALID_CREDENTIALS, e);
		} catch (OAuthConnectionException e) {
			throw new ApiLoginException(ApiLoginException.LoginExceptionReason.ERROR, e);
		} catch (RuntimeException e) {
			throw new ApiRuntimeException(e);
		}

		return response;
	}

	@Override
	public String toString() {
		return "OAuthLoginController [delegate=" + this.delegate + "]";
	}

}
