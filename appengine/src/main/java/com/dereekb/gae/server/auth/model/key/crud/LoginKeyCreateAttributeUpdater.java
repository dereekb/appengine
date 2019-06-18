package com.dereekb.gae.server.auth.model.key.crud;

import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.model.crud.task.impl.delegate.impl.AbstractChainedUpdateTaskDelegate;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.crud.LoginRelatedModelAttributeUtility;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.model.roles.impl.CrudModelRole;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyModelKeyUtil;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;
import com.googlecode.objectify.Key;

/**
 * {@link LoginKeyAttributeUpdater} extension used specifically for the
 * create-case.
 *
 * @author dereekb
 *
 */
public class LoginKeyCreateAttributeUpdater extends AbstractChainedUpdateTaskDelegate<LoginKey> {

	public static final String LOGIN_REQUIRED_CODE = "LOGIN_REQUIRED";

	private LoginRelatedModelAttributeUtility loginAttributeUtility;

	public LoginKeyCreateAttributeUpdater(UpdateTaskDelegate<LoginKey> updateTaskDelegate,
	        LoginRelatedModelAttributeUtility loginAttributeUtility) {
		super(updateTaskDelegate);
		this.setLoginAttributeUtility(loginAttributeUtility);
	}

	public LoginRelatedModelAttributeUtility getLoginAttributeUtility() {
		return this.loginAttributeUtility;
	}

	public void setLoginAttributeUtility(LoginRelatedModelAttributeUtility loginAttributeUtility) {
		if (loginAttributeUtility == null) {
			throw new IllegalArgumentException("loginAttributeUtility cannot be null.");
		}

		this.loginAttributeUtility = loginAttributeUtility;
	}

	// MARK: Create
	@Override
	protected void chainUpdateTarget(LoginKey target,
	                                 LoginKey template)
	        throws InvalidAttributeException {

		// Set Login
		Key<Login> login = loadLoginKeyWithTemplate(template);
		template.setLogin(login);
	}

	// TODO: Need to update. Should load the specified login pointer first and validate the login matches, or override the login if an admin.

	private Key<Login> loadLoginKeyWithTemplate(LoginKey template) throws InvalidAttributeException {

		Key<Login> login = template.getLogin();

		if (login == null) {
			try {
				LoginTokenUserDetails<LoginToken> details = LoginSecurityContext.getPrincipal();
				ModelKey loginKey = details.getLoginKey();
				login = ObjectifyModelKeyUtil.safeKeyFromNumber(Login.class, loginKey);
			} catch (NoSecurityContextException e) {
				throw new InvalidAttributeException("login", null, "Login is required.", LOGIN_REQUIRED_CODE);
			}
		}

		// Assert exists and user has owner permissions of the Login.
		this.loginAttributeUtility.makeInstanceWithTemplate(template).assertHasRole(CrudModelRole.OWNED);

		return login;
	}

}
