package com.dereekb.gae.server.auth.model.key.crud;

import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginStatusService;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginStatusServiceManager;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginUnavailableException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedGetter;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;
import com.googlecode.objectify.Key;

/**
 * {@link UpdateTaskDelegate} that sets the current login's API LoginKey as the
 * proper key if the target being updated is new and has no LoginKey set.
 * 
 * The item is then passed to another {@link UpdateTaskDelegate}.
 * 
 * @author dereekb
 *
 */
public class LoginKeyAuthenticationAttributeUpdater
        implements UpdateTaskDelegate<LoginKey> {

	private UpdateTaskDelegate<LoginKey> updateTask;
	private ObjectifyKeyedGetter<LoginPointer> loginPointerGetter;
	private KeyLoginStatusServiceManager statusServiceManager;

	public LoginKeyAuthenticationAttributeUpdater(UpdateTaskDelegate<LoginKey> updateTask,
	        ObjectifyKeyedGetter<LoginPointer> loginPointerGetter,
	        KeyLoginStatusServiceManager statusServiceManager) throws IllegalArgumentException {
		super();
		this.setUpdateTask(updateTask);
		this.setLoginPointerGetter(loginPointerGetter);
		this.setStatusServiceManager(statusServiceManager);
	}

	public UpdateTaskDelegate<LoginKey> getUpdateTask() {
		return this.updateTask;
	}

	public void setUpdateTask(UpdateTaskDelegate<LoginKey> updateTask) throws IllegalArgumentException {
		if (updateTask == null) {
			throw new IllegalArgumentException();
		}

		this.updateTask = updateTask;
	}

	public ObjectifyKeyedGetter<LoginPointer> getLoginPointerGetter() {
		return this.loginPointerGetter;
	}

	public void setLoginPointerGetter(ObjectifyKeyedGetter<LoginPointer> loginPointerGetter)
	        throws IllegalArgumentException {
		if (loginPointerGetter == null) {
			throw new IllegalArgumentException();
		}

		this.loginPointerGetter = loginPointerGetter;
	}

	public KeyLoginStatusServiceManager getStatusServiceManager() {
		return this.statusServiceManager;
	}

	public void setStatusServiceManager(KeyLoginStatusServiceManager statusServiceManager)
	        throws IllegalArgumentException {
		if (statusServiceManager == null) {
			throw new IllegalArgumentException();
		}

		this.statusServiceManager = statusServiceManager;
	}

	// MARK: UpdateTaskDelegate
	@Override
	public void updateTarget(LoginKey target,
	                         LoginKey template)
	        throws InvalidAttributeException {

		// Linking is done at creation.
		if (target.getLoginPointer() == null) {
			Key<LoginPointer> templatePointerKey = template.getLoginPointer();

			LoginTokenAuthentication<LoginToken> authentication = null;

			try {
				authentication = LoginSecurityContext.getAuthentication();
			} catch (NoSecurityContextException e) {
				throw new InvalidAttributeException("pointer", null, "No authentication available.");
			}

			Key<LoginPointer> loginPointerKey = null;

			// If the template doesn't have any pointer on it, get it from the
			// current user.
			if (templatePointerKey == null) {
				loginPointerKey = this.getKeyForCurrentUser(authentication);

				if (loginPointerKey == null) {
					throw new InvalidAttributeException("pointer", null, "Login Key API not enabled for this account.");
				}

				// If the template does, then check that the current user is an
				// admin who can set arbitrary values.
			} else if (authentication.getPrincipal().isAdministrator()) {
				loginPointerKey = templatePointerKey;
			} else {
				throw new InvalidAttributeException("pointer", templatePointerKey.getName(),
				        "Not permitted to set arbitrary pointer.");
			}

			if (loginPointerKey != null && this.isApiKeyPointer(loginPointerKey)) {
				target.setLoginPointer(loginPointerKey);
			} else {
				throw new InvalidAttributeException("pointer", templatePointerKey.getName(),
				        "Pointer is not an API key.");
			}

		}

		this.updateTask.updateTarget(target, template);
	}

	private boolean isApiKeyPointer(Key<LoginPointer> loginPointerKey) {
		LoginPointer pointer = this.loginPointerGetter.get(loginPointerKey);
		boolean isApiKeyPointer = false;

		if (pointer != null) {
			isApiKeyPointer = pointer.getLoginPointerType().equals(LoginPointerType.API_KEY);
		}

		return isApiKeyPointer;
	}

	private Key<LoginPointer> getKeyForCurrentUser(LoginTokenAuthentication<LoginToken> authentication) {
		Login login = authentication.getPrincipal().getLogin();
		KeyLoginStatusService statusService = this.statusServiceManager.getService(login);

		try {
			return statusService.getKeyLoginPointerKey();
		} catch (KeyLoginUnavailableException e) {
			return null;
		}
	}

}
