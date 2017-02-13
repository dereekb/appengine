package com.dereekb.gae.server.auth.model.pointer.crud;

import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.web.api.util.attribute.exception.AttributeUpdateFailureException;

public class LoginPointerAttributeUpdater
        implements UpdateTaskDelegate<LoginPointer> {

	@Override
	public void updateTarget(LoginPointer target,
	                         LoginPointer template) throws AttributeUpdateFailureException {

		// TODO: Update using delegate depending on the type of the login
		// pointer.

	}

}
