package com.dereekb.gae.server.app.model.app.crud;

import java.util.logging.Logger;

import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link UpdateTaskDelegate} implementation for {@link App}.
 *
 * @author dereekb
 *
 */
public class AppAttributeUpdater
        implements UpdateTaskDelegate<App> {

	private static final Logger LOGGER = Logger.getLogger(AppAttributeUpdater.class.getName());

	// MARK: UpdateTaskDelegate
	@Override
	public void updateTarget(App target,
	                         App template)
	        throws InvalidAttributeException {

		// Name
		String name = template.getName();

		if (name != null) {
			target.setName(name);
		}

		// Only an admin can change the level and secret of an app.
		if (LoginSecurityContext.safeIsAdministrator()) {

			// System
			String systemKey = template.getSystemKey();

			if (systemKey != null && systemKey != target.getSystemKey()) {
				target.setSystemKey(systemKey);
				LOGGER.warning("Admin just changed the system key of App '" + target.getModelKey() + "' to: "
				        + target.getSystemKey());
			}

			// Level
			Integer levelCode = template.getLevelCode();

			if (levelCode != null && levelCode != target.getLevelCode()) {
				target.setLevelCode(levelCode);
				LOGGER.warning("Admin just changed the security level of App '" + target.getModelKey() + "' to: "
				        + target.getAppLoginSecurityLevel());
			}

			// Secret
			String secret = template.getSecret();

			if (secret != null && secret != target.getSecret()) {
				target.setSecret(secret);
				LOGGER.warning("Admin just changed the secret of App '" + target.getModelKey());
			}

		}

	}

}
