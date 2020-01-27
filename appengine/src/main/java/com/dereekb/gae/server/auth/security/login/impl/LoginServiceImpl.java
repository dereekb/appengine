package com.dereekb.gae.server.auth.security.login.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.LoginPointerKeyFormatter;
import com.dereekb.gae.server.auth.security.login.LoginPointerService;
import com.dereekb.gae.server.auth.security.login.LoginService;
import com.dereekb.gae.server.auth.security.login.exception.LoginDisabledException;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Abstract {@link LoginService} implementation.
 *
 * @author dereekb
 *
 */
public abstract class LoginServiceImpl
        implements LoginService {

	private LoginPointerKeyFormatter formatter;
	private LoginPointerService pointerService;

	public LoginServiceImpl(LoginPointerType type, LoginPointerService pointerService) throws IllegalArgumentException {
		this.setFormatter(new LoginPointerKeyFormatterImpl(type));
		this.setPointerService(pointerService);
	}

	public LoginServiceImpl(LoginPointerKeyFormatter formatter, LoginPointerService pointerService)
	        throws IllegalArgumentException {
		this.setFormatter(formatter);
		this.setPointerService(pointerService);
	}

	public LoginPointerKeyFormatter getFormatter() {
		return this.formatter;
	}

	public void setFormatter(LoginPointerKeyFormatter formatter) {
		if (formatter == null) {
			throw new IllegalArgumentException("formatter cannot be null.");
		}

		this.formatter = formatter;
	}

	public LoginPointerService getPointerService() {
		return this.pointerService;
	}

	public void setPointerService(LoginPointerService pointerService) throws IllegalArgumentException {
		if (pointerService == null) {
			throw new IllegalArgumentException("Encoder cannot be null.");
		}

		this.pointerService = pointerService;
	}

	// MARK: LoginService
	@Override
	public LoginPointer getLogin(String username) throws LoginDisabledException, LoginUnavailableException {
		LoginPointer pointer = this.loadLogin(username);

		if (pointer == null) {
			throw new LoginUnavailableException(username);
		}

		return pointer;
	}

	protected LoginPointer createLogin(String username,
	                                   LoginPointer template)
	        throws LoginExistsException {
		ModelKey key = this.formatter.getKeyForUsername(username);
		return this.pointerService.createLoginPointer(key, template);
	}

	protected LoginPointer loadLogin(String username) throws LoginDisabledException {
		ModelKey key = this.formatter.getKeyForUsername(username);
		return this.pointerService.getLoginPointer(key);
	}

	@Override
	public String toString() {
		return "LoginServiceImpl [formatter=" + this.formatter + ", pointerService=" + this.pointerService + "]";
	}

}
