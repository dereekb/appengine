package com.dereekb.gae.server.auth.model.login.crud;

import java.util.Date;

import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.utilities.time.DateUtility;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

public class LoginAttributeUpdater
        implements UpdateTaskDelegate<Login> {

	public static final String DISABLING_DISALLOWED_CODE = "DISABLING_DISALLOWED";
	public static final String INVALID_AUTH_RESET_CODE = "INVALID_AUTH_RESET";

	@Override
	public void updateTarget(Login target,
	                         Login template)
	        throws InvalidAttributeException {

		if (template.getRoles() != null) {
			target.setRoles(template.getRoles());
		}

		// Auth Reset
		Date authReset = target.getAuthReset();

		if (authReset != null) {
			if (DateUtility.dateIsAfterDate(authReset, target.getAuthReset())) {
				target.setAuthReset(new Date());
			} else {
				throw new InvalidAttributeException("authReset", template.getAuthReset(),
				        "New value must be after the current value on the target.", INVALID_AUTH_RESET_CODE);
			}
		}

		// Disable
		if (template.getDisabled() != null) {
			if (template.getDisabled() == true && target.getDisabled() == false) {

				if (!LoginSecurityContext.isAdministrator()) {
					throw new InvalidAttributeException("disabled", true, "You are not allowed to disable this.",
					        DISABLING_DISALLOWED_CODE);
				} else {
					target.setDisabled(true);
					target.setDisabledSync(false);

					String disabledReason = template.getDisabledReason();

					if (disabledReason != null) {
						target.setDisabledReason(disabledReason);
					}

					target.setAuthReset(new Date());
				}
			}
		}

	}

}
