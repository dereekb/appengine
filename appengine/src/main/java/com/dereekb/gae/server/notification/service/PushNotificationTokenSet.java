package com.dereekb.gae.server.notification.service;

import java.util.List;

/**
 * Container for multiple {@link PushNotificationToken} values.
 *
 * @author dereekb
 *
 */
public interface PushNotificationTokenSet {

	/**
	 * Returns all tokens for the request.
	 *
	 * @return {@link List}. Never {@code null}, or empty.
	 */
	public List<PushNotificationToken> getTokens();

}
