package com.dereekb.gae.web.api.debug;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.LoginRegisterService;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginRegistrationRejectedException;
import com.dereekb.gae.server.auth.security.login.password.PasswordLoginService;
import com.dereekb.gae.utilities.misc.random.StringLongGenerator;
import com.dereekb.gae.web.api.auth.controller.password.impl.PasswordLoginPairImpl;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * Debug controller. Only usable in development modes.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/debug")
public class LoginServiceDebugController extends AbstractDebugController {

	private PasswordLoginService passwordLoginService;
	private LoginRegisterService registerService;

	public LoginServiceDebugController(PasswordLoginService passwordLoginService,
	        LoginRegisterService registerService) {
		super();
		this.setPasswordLoginService(passwordLoginService);
		this.setRegisterService(registerService);
	}

	public PasswordLoginService getPasswordLoginService() {
		return this.passwordLoginService;
	}

	public void setPasswordLoginService(PasswordLoginService passwordLoginService) {
		if (passwordLoginService == null) {
			throw new IllegalArgumentException("passwordLoginService cannot be null.");
		}

		this.passwordLoginService = passwordLoginService;
	}

	public LoginRegisterService getRegisterService() {
		return this.registerService;
	}

	public void setRegisterService(LoginRegisterService registerService) {
		if (registerService == null) {
			throw new IllegalArgumentException("registerService cannot be null.");
		}

		this.registerService = registerService;
	}

	// MARK: Inputs
	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.POST, produces = "application/json")
	public final ApiResponse makeUser() {
		ApiResponseImpl response = new ApiResponseImpl();

		String username = StringLongGenerator.randomString();
		String password = "password";

		try {
			LoginPointer pointer = this.passwordLoginService.create(new PasswordLoginPairImpl(username, password),
			        true);
			Login login = this.registerService.register(pointer);
			this.initalizeNewLogin(login);
		} catch (LoginExistsException | LoginRegistrationRejectedException e) {
			RuntimeExceptionResolver.resolve(new RuntimeException(e));
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	protected void initalizeNewLogin(Login login) {
		// Override in super classes optionally.
	}

}
