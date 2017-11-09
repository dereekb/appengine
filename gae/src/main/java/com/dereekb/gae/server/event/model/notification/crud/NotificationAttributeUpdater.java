package com.dereekb.gae.server.event.model.notification.crud;

import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.server.event.model.notification.Notification;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link UpdateTaskDelegate} for a {@link Notification}.
 * 
 * @author dereekb
 *
 */
public class NotificationAttributeUpdater
        implements UpdateTaskDelegate<Notification> {

	@Override
	public void updateTarget(Notification target,
	                         Notification template)
	        throws InvalidAttributeException {
		Boolean read = template.getRead();
		
		if (read != null) {
			target.setRead(read);
		}
	}

}
