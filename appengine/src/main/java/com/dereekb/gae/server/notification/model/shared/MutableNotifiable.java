package com.dereekb.gae.server.notification.model.shared;

/**
 * Mutable {@link Notifiable} model
 *
 * @author dereekb
 *
 */
public interface MutableNotifiable
        extends Notifiable {

	public boolean setNotified(boolean notified);

}
