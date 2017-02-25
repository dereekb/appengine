package com.dereekb.gae.server.auth.security.login.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.LoginRegisterService;
import com.dereekb.gae.server.auth.security.login.NewLoginGenerator;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginPointerRegisteredException;
import com.dereekb.gae.server.auth.security.login.exception.LoginRegistrationRejectedException;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.googlecode.objectify.Key;

/**
 * {@link LoginRegisterService} implementation.
 *
 * @author dereekb
 *
 */
public class LoginRegisterServiceImpl
        implements LoginRegisterService {

	private NewLoginGenerator loginGenerator;

	private GetterSetter<Login> loginGetterSetter;
	private GetterSetter<LoginPointer> loginPointerGetterSetter;

	public LoginRegisterServiceImpl(NewLoginGenerator loginGenerator,
	        GetterSetter<Login> loginGetterSetter,
	        GetterSetter<LoginPointer> loginPointerGetterSetter) throws IllegalArgumentException {
		super();
		this.setLoginGenerator(loginGenerator);
		this.setLoginGetterSetter(loginGetterSetter);
		this.setLoginPointerGetterSetter(loginPointerGetterSetter);
	}

	public NewLoginGenerator getLoginGenerator() {
		return this.loginGenerator;
	}

	public void setLoginGenerator(NewLoginGenerator loginGenerator) throws IllegalArgumentException {
		if (loginGenerator == null) {
			throw new IllegalArgumentException("LoginGenerator cannot be null.");
		}

		this.loginGenerator = loginGenerator;
	}

	public GetterSetter<Login> getLoginGetterSetter() {
		return this.loginGetterSetter;
	}

	public void setLoginGetterSetter(GetterSetter<Login> loginGetterSetter) throws IllegalArgumentException {
		if (loginGetterSetter == null) {
			throw new IllegalArgumentException("LoginGetterSetter cannot be null.");
		}

		this.loginGetterSetter = loginGetterSetter;
	}

	public GetterSetter<LoginPointer> getLoginPointerGetterSetter() {
		return this.loginPointerGetterSetter;
	}

	public void setLoginPointerGetterSetter(GetterSetter<LoginPointer> loginPointerGetterSetter)
	        throws IllegalArgumentException {
		if (loginPointerGetterSetter == null) {
			throw new IllegalArgumentException("LoginPointerGetterSetter cannot be null.");
		}

		this.loginPointerGetterSetter = loginPointerGetterSetter;
	}

	// MARK: LoginRegisterService
	@Override
	public Login register(LoginPointer pointer) throws LoginExistsException, LoginRegistrationRejectedException {

		if (pointer == null) {
			throw new IllegalArgumentException("Pointer cannot be null.");
		}

		if (pointer.getLogin() != null) {
			throw new LoginExistsException();
		}

		Login login = this.loginGenerator.makeLogin(pointer);

		Set<String> loginPointers = new HashSet<>(1);
		loginPointers.add(pointer.getIdentifier());

		this.registerPointersToLogin(login, loginPointers);
		return login;
	}

	@Override
	public void registerPointersToLogin(ModelKey loginKey,
	                                    Set<String> loginPointers)
	        throws LoginPointerRegisteredException,
	            AtomicOperationException {

		Login login = this.loginGetterSetter.get(loginKey);

		if (login == null) {
			throw new AtomicOperationException("Could not load Login.", AtomicOperationExceptionReason.UNAVAILABLE);
		}

		this.registerPointersToLogin(login, loginPointers);
	}

	public void registerPointersToLogin(Login login,
	                                    Set<String> loginPointers)
	        throws LoginPointerRegisteredException {

		List<ModelKey> loginPointerKeysArray = ModelKey.convert(ModelKeyType.NAME, loginPointers);
		Set<ModelKey> loginPointerKeys = new HashSet<ModelKey>(loginPointerKeysArray);
		List<LoginPointer> pointers = this.loginPointerGetterSetter.getWithKeys(loginPointerKeys);

		if (pointers.size() != loginPointerKeys.size()) {
			throw new AtomicOperationException("Could not load all requested pointers.",
			        AtomicOperationExceptionReason.UNAVAILABLE);
		}

		Key<Login> loginKey = login.getObjectifyKey();
		String ownerId = login.getOwnerId();

		this.assertPointersAreUnclaimed(loginKey, pointers);

		for (LoginPointer pointer : pointers) {
			pointer.setLogin(loginKey);
			pointer.setOwnerId(ownerId);
		}

		this.loginPointerGetterSetter.save(pointers, true);
	}

	private void assertPointersAreUnclaimed(Key<Login> newLogin,
	                                        List<LoginPointer> pointers)
	        throws LoginPointerRegisteredException {
		for (LoginPointer pointer : pointers) {
			Key<Login> currentLogin = pointer.getLogin();

			if (currentLogin != null && currentLogin.equivalent(newLogin) == false) {
				throw new LoginPointerRegisteredException(pointer);
			}
		}
	}

}
