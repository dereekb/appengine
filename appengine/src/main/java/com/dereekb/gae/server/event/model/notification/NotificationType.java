package com.dereekb.gae.server.event.model.notification;

/**
 * {@link Notification} type.
 * 
 * @author dereekb
 *
 */
public enum NotificationType {

	NOTIFICATION(0);

	public final int code;

	private NotificationType(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}

}
