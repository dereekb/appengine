package com.dereekb.gae.server.auth.model.pointer.crud;

import com.dereekb.gae.model.crud.exception.AttributeFailureException;
import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

public class LoginPointerAttributeUpdater
        implements UpdateTaskDelegate<LoginPointer> {

	@Override
	public void updateTarget(LoginPointer target,
	                         LoginPointer template) throws AttributeFailureException {

		// TODO: Update using delegate depending on the type of the login
		// pointer.

	}

}
