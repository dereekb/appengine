package com.dereekb.gae.server.auth.security.login.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.LoginPointerService;
import com.dereekb.gae.server.auth.security.login.LoginService;
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

	private String format;
	private LoginPointerService pointerService;

	public LoginServiceImpl(String format, LoginPointerService pointerService) throws IllegalArgumentException {
		this.setFormat(format);
		this.setPointerService(pointerService);
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
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
	public LoginPointer getLogin(String username) throws LoginUnavailableException {
		LoginPointer pointer = this.loadLogin(username);

		if (pointer == null) {
			throw new LoginUnavailableException(username);
		}

		return pointer;
	}

	protected LoginPointer createLogin(String username,
	                                   LoginPointer template)
	        throws LoginExistsException {
		ModelKey key = this.getLoginPointerKey(username);
		return this.pointerService.createLoginPointer(key, template);
	}

	protected LoginPointer loadLogin(String username) {
		ModelKey key = this.getLoginPointerKey(username);
		return this.pointerService.getLoginPointer(key);
	}

	protected ModelKey getLoginPointerKey(String username) {
		String id = this.getLoginIdForUsername(username);
		return new ModelKey(id);
	}

	protected String getLoginIdForUsername(String username) {
		return String.format(this.format, username.toLowerCase());
	}

	@Override
	public String toString() {
		return "LoginServiceImpl [format=" + this.format + ", pointerService=" + this.pointerService + "]";
	}

}
