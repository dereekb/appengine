package com.dereekb.gae.server.notification.model.token.crud;

import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.server.notification.model.token.NotificationSettings;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

public class NotificationSettingsAttributeUpdater
        implements UpdateTaskDelegate<NotificationSettings> {

	@Override
	public void updateTarget(NotificationSettings target,
	                         NotificationSettings template)
	        throws InvalidAttributeException {
		// Do nothing.
	}

}
