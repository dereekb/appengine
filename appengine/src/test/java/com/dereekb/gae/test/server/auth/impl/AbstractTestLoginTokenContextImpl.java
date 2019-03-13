package com.dereekb.gae.test.server.auth.impl;

import com.dereekb.gae.model.extension.generation.impl.keys.LongModelKeyGenerator;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.server.auth.TestLoginTokenContext;
import com.dereekb.gae.test.server.auth.TestLoginTokenPair;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * Abstract {@link TestLoginTokenContext} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractTestLoginTokenContextImpl
        implements TestLoginTokenContext {

	protected static final String TEST_USERNAME = "username";
	protected static final String TEST_PASSWORD = "password";

	private boolean refreshAllowed = true;

	private Factory<ModelKey> nameFactory = new LongModelKeyGenerator();

	private Long encodedRoles = null;
	private Long encodedAdminRoles = null;
	private Integer group = null;

	private boolean defaultToAnonymous = true;

	private String username = TEST_USERNAME;
	private String password = TEST_PASSWORD;

	public boolean isRefreshAllowed() {
		return this.refreshAllowed;
	}

	public void setRefreshAllowed(boolean refreshAllowed) {
		this.refreshAllowed = refreshAllowed;
	}

	public Long getEncodedRoles() {
		return this.encodedRoles;
	}

	public void setEncodedRoles(Long encodedRoles) {
		this.encodedRoles = encodedRoles;
	}

	public Long getEncodedAdminRoles() {
		return this.encodedAdminRoles;
	}

	public void setEncodedAdminRoles(Long encodedAdminRoles) {
		this.encodedAdminRoles = encodedAdminRoles;
	}

	public Integer getGroup() {
		return this.group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isDefaultToAnonymous() {
		return this.defaultToAnonymous;
	}

	public void setDefaultToAnonymous(boolean defaultToAnonymous) {
		this.defaultToAnonymous = defaultToAnonymous;
	}

	// MARK: TestLoginTokenContext
	@Override
	public abstract String getToken();

	@Override
	public abstract String generateAnonymousToken();

	@Override
	public void generateAnonymousLogin() {
		this.clearLogin();
		this.setDefaultToAnonymous(true);
	}

	@Override
	public TestLoginTokenPair generateSystemAdmin() {
		return this.generateSystemAdmin(this.nameFactory.make().toString());
	}

	@Override
	public TestLoginTokenPair generateSystemAdmin(String username) {
		return this.generateLogin(username, this.encodedAdminRoles);
	}

	@Override
	public TestLoginTokenPair generateLogin() {
		return this.generateLogin(this.nameFactory.make().toString());
	}

	@Override
	public TestLoginTokenPair generateLogin(String username) {
		return this.generateLogin(username, this.encodedRoles);
	}

	// MARK: Pair
	/**
	 * {@link TestLoginTokenPair} implementation.
	 *
	 * @author dereekb
	 *
	 */
	protected abstract class AbstractTestLoginTokenPairImpl
	        implements TestLoginTokenPair {

		private Login login;
		private LoginPointer loginPointer;
		private String token;

		public AbstractTestLoginTokenPairImpl(Login login, LoginPointer loginPointer) {
			this.setLogin(login);
			this.setLoginPointer(loginPointer);
		}

		@Override
		public Login getLogin() {
			return this.login;
		}

		public void setLogin(Login login) {
			if (login == null) {
				throw new IllegalArgumentException("login cannot be null.");
			}

			this.login = login;
		}

		@Override
		public LoginPointer getLoginPointer() {
			return this.loginPointer;
		}

		public void setLoginPointer(LoginPointer loginPointer) {
			if (loginPointer == null) {
				throw new IllegalArgumentException("loginPointer cannot be null.");
			}

			this.loginPointer = loginPointer;
		}

		@Override
		public String getTokenSignature() {
			return null;
		}

		@Override
		public String getEncodedLoginToken() {
			if (this.token == null) {
				this.token = this.regenerateToken();
			}

			return this.token;
		}

		@Override
		public final String regenerateToken() {
			this.token = this.makeNewToken();
			return this.token;
		}

		protected abstract String makeNewToken();

	}

}
