package com.dereekb.gae.server.auth.security.login.key.impl;

import java.util.List;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.model.pointer.search.query.LoginPointerQueryInitializer;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginStatusService;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginStatusServiceManager;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginExistsException;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginUnavailableException;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestOptionsImpl;
import com.googlecode.objectify.Key;

/**
 * {@link KeyLoginStatusServiceManager} implementation.
 * 
 * @author dereekb
 *
 */
public class KeyLoginStatusServiceManagerImpl
        implements KeyLoginStatusServiceManager {

	private String DEFAULT_FORMAT = "%s_API";
	
	private String loginPointerFormat = DEFAULT_FORMAT;
	
	private ObjectifyRegistry<LoginPointer> loginPointerRegistry;

	public KeyLoginStatusServiceManagerImpl(ObjectifyRegistry<LoginPointer> loginPointerRegistry)
	        throws IllegalArgumentException {
		this.setLoginPointerRegistry(loginPointerRegistry);
	}

	public String getLoginPointerFormat() {
		return loginPointerFormat;
	}
	
	public void setLoginPointerFormat(String loginPointerFormat) {
		this.loginPointerFormat = loginPointerFormat;
	}

	public ObjectifyRegistry<LoginPointer> getLoginPointerRegistry() {
		return loginPointerRegistry;
	}

	public void setLoginPointerRegistry(ObjectifyRegistry<LoginPointer> loginPointerRegistry)
	        throws IllegalArgumentException {
		if (loginPointerRegistry == null) {
			throw new IllegalArgumentException("Registry cannot be null.");
		}

		this.loginPointerRegistry = loginPointerRegistry;
	}

	// MARK: KeyLoginStatusServiceManager
	@Override
	public KeyLoginStatusService getService(Login login) {
		return new KeyLoginStatusServiceImpl(login);
	}

	/**
	 * {@link KeyLoginStatusService} implementation.
	 * 
	 * NOTE: Will only function properly if the index is properly setup to index
	 * login with type.
	 * 
	 * @author dereekb
	 *
	 */
	public class KeyLoginStatusServiceImpl
	        implements KeyLoginStatusService {

		private Login login;
		private Key<LoginPointer> cachedLoginPointer;

		public KeyLoginStatusServiceImpl(Login login) {
			if (login == null) {
				throw new IllegalArgumentException("Login cannot be null.");
			}

			this.login = login;
		}

		// MARK: KeyLoginStatusService
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
			return loginPointerRegistry.get(keyLoginPointer);
		}

		@Override
		public boolean isEnabled() {
			boolean isEnabled = true;
			
			try {
			this.getKeyLoginPointerKey();
			} catch (KeyLoginUnavailableException e) {
				isEnabled = false;
			}
			
			return isEnabled;
		}
		
		@Override
		public LoginPointer enable() throws KeyLoginExistsException {
			try {
				this.getKeyLoginPointerKey();
				throw new KeyLoginExistsException();
			} catch (KeyLoginUnavailableException e) {

				Key<Login> loginKey = this.login.getObjectifyKey();
				Long id = loginKey.getId();
				
				LoginPointer pointer = new LoginPointer();
				String pointerString = String.format(loginPointerFormat, id);
				
				pointer.setIdentifier(pointerString);
				pointer.setLogin(loginKey);
				pointer.setLoginPointerType(LoginPointerType.API_KEY);

				loginPointerRegistry.save(pointer, false);

				this.cachedLoginPointer = pointer.getObjectifyKey();
				
				return pointer;
			}
		}

		// MARK: Internal
		private Key<LoginPointer> queryKeyLoginPointerKey() throws KeyLoginUnavailableException {
			ObjectifyQueryRequestBuilder<LoginPointer> queryBuilder = loginPointerRegistry.makeQuery();
			LoginPointerQueryInitializer.ObjectifyLoginPointerQuery queryConfig = new LoginPointerQueryInitializer.ObjectifyLoginPointerQuery();

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

	@Override
	public String toString() {
		return "KeyLoginStatusServiceManagerImpl [loginPointerRegistry=" + loginPointerRegistry + "]";
	}
	
}
