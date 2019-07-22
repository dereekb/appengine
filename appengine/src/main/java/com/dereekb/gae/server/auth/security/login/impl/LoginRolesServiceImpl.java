package com.dereekb.gae.server.auth.security.login.impl;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.security.login.LoginRolesService;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LoginRolesService} implementation.
 *
 * @author dereekb
 *
 */
public class LoginRolesServiceImpl
        implements LoginRolesService {

	private Long adminRoles;
	private Long defaultRoles;

	private GetterSetter<Login> loginSetter;

	public LoginRolesServiceImpl(GetterSetter<Login> loginSetter, Long adminRoles) {
		this(loginSetter, adminRoles, Login.DEFAULT_ROLES);
	}

	public LoginRolesServiceImpl(GetterSetter<Login> loginSetter, Long adminRoles, Long defaultRoles) {
		super();
		this.setLoginSetter(loginSetter);
		this.setAdminRoles(adminRoles);
		this.setDefaultRoles(defaultRoles);
	}

	public Long getAdminRoles() {
		return this.adminRoles;
	}

	public void setAdminRoles(Long adminRoles) {
		if (adminRoles == null) {
			throw new IllegalArgumentException("adminRoles cannot be null.");
		}

		this.adminRoles = adminRoles;
	}

	public Long getDefaultRoles() {
		return this.defaultRoles;
	}

	public void setDefaultRoles(Long defaultRoles) {
		if (defaultRoles == null) {
			throw new IllegalArgumentException("defaultRoles cannot be null.");
		}

		this.defaultRoles = defaultRoles;
	}

	public GetterSetter<Login> getLoginSetter() {
		return this.loginSetter;
	}

	public void setLoginSetter(GetterSetter<Login> loginSetter) {
		if (loginSetter == null) {
			throw new IllegalArgumentException("loginSetter cannot be null.");
		}

		this.loginSetter = loginSetter;
	}

	// MARK: LoginRolesService
	@Override
	public Login makeAdmin(ModelKey key) throws LoginUnavailableException {
		Login login = this.getLogin(key);

		login.setRoles(this.adminRoles);
		this.loginSetter.update(login);

		return login;
	}

	@Override
	public Login clearRoles(ModelKey key) throws LoginUnavailableException {
		Login login = this.getLogin(key);

		login.setRoles(this.defaultRoles);
		this.loginSetter.update(login);

		return login;
	}

	// MARK: Internal
	protected Login getLogin(ModelKey key) throws LoginUnavailableException {

		Login login = this.loginSetter.get(key);

		if (login == null) {
			throw new LoginUnavailableException(key);
		}

		return login;
	}

	@Override
	public String toString() {
		return "LoginRolesServiceImpl [adminRoles=" + this.adminRoles + ", defaultRoles=" + this.defaultRoles
		        + ", loginSetter=" + this.loginSetter + "]";
	}

}
