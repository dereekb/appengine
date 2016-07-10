package com.dereekb.gae.server.auth.security.login.impl;

import java.util.Set;

import com.dereekb.gae.model.extension.links.exception.LinkException;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.LoginRegisterService;
import com.dereekb.gae.server.auth.security.login.LoginService;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Abstract {@link LoginService} implementation.
 *
 * @author dereekb
 *
 */
public class LoginServiceImpl
        implements LoginService {

	private String format;
	private GetterSetter<LoginPointer> getterSetter;

	private LoginRegisterService registerService;

	public LoginServiceImpl(String format, GetterSetter<LoginPointer> getterSetter) throws IllegalArgumentException {
		this.setFormat(format);
		this.setGetterSetter(getterSetter);
	}

	public String getFormat() {
		return this.format;
    }

	public void setFormat(String format) {
		this.format = format;
    }

	public GetterSetter<LoginPointer> getGetterSetter() {
		return this.getterSetter;
	}

	public void setGetterSetter(GetterSetter<LoginPointer> getterSetter) throws IllegalArgumentException {
		if (getterSetter == null) {
			throw new IllegalArgumentException("Encoder cannot be null.");
		}

		this.getterSetter = getterSetter;
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
	                                   LoginPointer template) throws LoginExistsException {
		ModelKey key = this.getLoginPointerKey(username);
		boolean exists = this.getterSetter.exists(key);

		if (exists) {
			throw new LoginExistsException(username);
		}

		template.setModelKey(key);
		this.getterSetter.save(template, false);
		return template;
	}

	protected LoginPointer loadLogin(String username) {
		ModelKey key = this.getLoginPointerKey(username);
		return this.getterSetter.get(key);
	}

	protected ModelKey getLoginPointerKey(String username) {
		String id = this.getLoginIdForUsername(username);
		return new ModelKey(id);
	}

	protected String getLoginIdForUsername(String username) {
		return String.format(this.format, username.toLowerCase());
	}

	// MARK: LoginRegisterService
	@Override
	public Login register(LoginPointer pointer) throws LoginExistsException {
		return this.registerService.register(pointer);
	}

	@Override
	public void registerLogins(ModelKey loginKey,
	                           Set<String> loginPointers) throws LinkException {
		this.registerService.registerLogins(loginKey, loginPointers);
	}

}
