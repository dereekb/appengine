package com.dereekb.gae.server.notification.service.impl;

import java.util.List;

import com.dereekb.gae.server.notification.service.PushNotificationToken;
import com.dereekb.gae.server.notification.service.PushNotificationTokenSet;

/**
 * {@link PushNotificationTokenSet} implementation.
 *
 * @author dereekb
 *
 */
public class PushNotificationTokenSetImpl
        implements PushNotificationTokenSet {

	private List<PushNotificationToken> tokens;

	public PushNotificationTokenSetImpl(List<PushNotificationToken> tokens) {
		super();
		this.setTokens(tokens);
	}

	// MARK: PushNotificationTokenSet
	@Override
	public List<PushNotificationToken> getTokens() {
		return this.tokens;
	}

	public void setTokens(List<PushNotificationToken> tokens) {
		if (tokens == null || tokens.isEmpty()) {
			throw new IllegalArgumentException("tokens cannot be null or empty.");
		}

		this.tokens = tokens;
	}

	@Override
	public String toString() {
		return "PushNotificationTokenSetImpl [tokens=" + this.tokens + "]";
	}

}
