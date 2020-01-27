package com.dereekb.gae.server.auth.model.pointer.crud;

import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

public class LoginPointerAttributeUpdater
        implements UpdateTaskDelegate<LoginPointer> {

	public static final String DISABLING_DISALLOWED_CODE = "DISABLING_DISALLOWED";

	@Override
	public void updateTarget(LoginPointer target,
	                         LoginPointer template) throws InvalidAttributeException {

		// TODO: Update using delegate depending on the type of the login
		// pointer.

		// Disable
		if (template.getDisabled() != null) {
			if (template.getDisabled() == true && target.getDisabled() == false) {

				if (!LoginSecurityContext.isAdministrator()) {
					throw new InvalidAttributeException("disabled", true, "You are not allowed to disable this.",
					        DISABLING_DISALLOWED_CODE);
				} else {
					template.setDisabled(true);
				}
			}
		}

	}

}
