package com.dereekb.gae.server.auth.security.login.impl;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.NewLoginGenerator;
import com.dereekb.gae.server.auth.security.login.NewLoginGeneratorDelegate;
import com.dereekb.gae.server.datastore.Setter;

/**
 * {@link NewLoginGenerator} implementation.
 *
 * @author dereekb
 *
 */
public class NewLoginGeneratorImpl
        implements NewLoginGenerator, NewLoginGeneratorDelegate {

	private Setter<Login> loginSetter;
	private NewLoginGeneratorDelegate delegate = this;

	public NewLoginGeneratorImpl(Setter<Login> loginSetter) {
		this.loginSetter = loginSetter;
	}

	public NewLoginGeneratorImpl(Setter<Login> loginSetter, NewLoginGeneratorDelegate delegate) {
		this.loginSetter = loginSetter;
		this.delegate = delegate;
	}

	public Setter<Login> getLoginSetter() {
		return this.loginSetter;
	}

	public void setLoginSetter(Setter<Login> loginSetter) {
		this.loginSetter = loginSetter;
	}

	public NewLoginGeneratorDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(NewLoginGeneratorDelegate delegate) {
		this.delegate = delegate;
	}

	// MARK: NewLoginGenerator
	@Override
	public Login makeLogin(LoginPointer pointer) {
		Login login = this.delegate.makeNewLogin(pointer);
		this.loginSetter.save(login, false);
		return login;
	}

	// MARK: NewLoginGeneratorDelegate
	@Override
	public Login makeNewLogin(LoginPointer pointer) {
		return new Login();
	}

	@Override
	public String toString() {
		return "NewLoginGeneratorImpl [loginSetter=" + this.loginSetter + ", delegate=" + this.delegate + "]";
	}

}
