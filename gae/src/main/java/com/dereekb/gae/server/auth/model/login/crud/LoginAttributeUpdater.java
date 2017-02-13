package com.dereekb.gae.server.auth.model.login.crud;

import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.web.api.util.attribute.exception.AttributeUpdateFailureException;

public class LoginAttributeUpdater
        implements UpdateTaskDelegate<Login> {

	@Override
	public void updateTarget(Login target,
	                         Login template) throws AttributeUpdateFailureException {

		if (template.getRoles() != null) {
			target.setRoles(template.getRoles());
		}

	}

}
