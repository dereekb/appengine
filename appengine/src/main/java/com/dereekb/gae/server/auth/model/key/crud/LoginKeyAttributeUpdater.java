package com.dereekb.gae.server.auth.model.key.crud;

import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

public class LoginKeyAttributeUpdater
        implements UpdateTaskDelegate<LoginKey> {

	@Override
	public void updateTarget(LoginKey target,
	                         LoginKey template) throws InvalidAttributeException {

		if (template.getName() != null) {
			target.setName(template.getName());
		}
		
		Long mask = template.getMask();

		if (mask != null) {
			
			//Zero and negative numbers become expired.
			if (mask <= 0) {
				mask = null;
			}
			
			target.setMask(mask);
		}
		
		if (template.getVerification() != null) {
			target.setVerification(template.getVerification());
		}
		
	}

}
