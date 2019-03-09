package com.dereekb.gae.server.auth.model.login.crud;

import com.dereekb.gae.model.crud.exception.AttributeFailureException;
import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.server.auth.model.login.Login;

public class LoginAttributeUpdater
        implements UpdateTaskDelegate<Login> {

	@Override
	public void updateTarget(Login target,
	                         Login template) throws AttributeFailureException {

		if (template.getRoles() != null) {
			target.setRoles(template.getRoles());
		}

	}

}
