package com.dereekb.gae.server.auth.security.login.key.impl;

import java.util.List;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.model.pointer.search.query.LoginPointerQueryInitializer;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginStatusService;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginExistsException;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginUnavailableException;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestOptionsImpl;
import com.googlecode.objectify.Key;

/**
 * {@link KeyLoginStatusService} implementation.
 * 
 * NOTE: Will only function properly if the index is properly setup to index login with type.
 * 
 * @author dereekb
 *
 */
public class KeyLoginStatusServiceImpl
        implements KeyLoginStatusService {

	private Login login;

	private ObjectifyRegistry<LoginPointer> loginPointerRegistry;

	private Key<LoginPointer> cachedLoginPointer;

	public KeyLoginStatusServiceImpl(Login login, ObjectifyRegistry<LoginPointer> loginPointerRegistry) {

		if (login == null || loginPointerRegistry == null) {
			throw new IllegalArgumentException("Login or Registry were null.");
		}
		
		this.login = login;
		this.loginPointerRegistry = loginPointerRegistry;
	}

	//MARK: KeyLoginStatusService
	@Override
	public Login getLogin() {
		return this.login;
	}

	@Override
	public Key<LoginPointer> getKeyLoginPointerKey() throws KeyLoginUnavailableException {
		if (this.cachedLoginPointer == null) {
			this.cachedLoginPointer = this.queryKeyLoginPointerKey();
		}

		return this.cachedLoginPointer;
	}
	
	@Override
	public LoginPointer getKeyLoginPointer() throws KeyLoginUnavailableException {
		Key<LoginPointer> keyLoginPointer = this.getKeyLoginPointerKey();
		return this.loginPointerRegistry.get(keyLoginPointer);
	}

	@Override
	public LoginPointer enable() throws KeyLoginExistsException {
		try {
			this.getKeyLoginPointerKey();
			throw new KeyLoginExistsException();
		} catch (KeyLoginUnavailableException e) {
			
			Key<Login> loginKey = this.login.getObjectifyKey();
			
			LoginPointer pointer = new LoginPointer();

			pointer.setLogin(loginKey);
			pointer.setLoginPointerType(LoginPointerType.API_KEY);
			
			this.loginPointerRegistry.save(pointer, false);
			
			return pointer;
		}
	}
	
	//MARK: Internal
	private Key<LoginPointer> queryKeyLoginPointerKey() throws KeyLoginUnavailableException {
		ObjectifyQueryRequestBuilder<LoginPointer> queryBuilder = loginPointerRegistry.makeQuery();
		LoginPointerQueryInitializer.LoginPointerQuery queryConfig = new LoginPointerQueryInitializer.LoginPointerQuery();

		Key<Login> loginKey = this.login.getObjectifyKey();
		queryConfig.setLogin(loginKey);
		queryConfig.setType(LoginPointerType.API_KEY);

		ObjectifyQueryRequestOptionsImpl options = new ObjectifyQueryRequestOptionsImpl();
		options.setLimit(1);

		queryConfig.configure(queryBuilder);
		queryBuilder.setOptions(options);

		ExecutableObjectifyQuery<LoginPointer> query = queryBuilder.buildExecutableQuery();
		List<Key<LoginPointer>> results = query.queryObjectifyKeys();

		if (results.size() == 0) {
			throw new KeyLoginUnavailableException();
		}

		return results.get(0);
	}

	@Override
	public String toString() {
		return "KeyLoginStatusServiceImpl [login=" + login + ", loginPointerRegistry=" + loginPointerRegistry + "]";
	}

}
