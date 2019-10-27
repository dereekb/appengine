package com.dereekb.gae.server.notification.service.impl;

import com.dereekb.gae.server.notification.service.PushNotificationSendRequest;
import com.dereekb.gae.server.notification.service.PushNotificationSendRequestBody;
import com.dereekb.gae.server.notification.service.PushNotificationTokenSet;
import com.dereekb.gae.utilities.misc.parameters.Parameters;

/**
 * {@link PushNotificationSendRequest] implementation.
 *
 * @author dereekb
 *
 */
public class PushNotificationSendRequestImpl extends PushNotificationSendRequestBodyImpl
        implements PushNotificationSendRequest {

	private PushNotificationTokenSet tokenSet;

	public PushNotificationSendRequestImpl(PushNotificationTokenSet tokenSet, String title) {
		this(tokenSet, title, null, null);
	}

	public PushNotificationSendRequestImpl(PushNotificationTokenSet tokenSet, String title, String message) {
		this(tokenSet, title, message, null);
	}

	public PushNotificationSendRequestImpl(PushNotificationTokenSet tokenSet,
	        String title,
	        String message,
	        Parameters data) {
		this(tokenSet, title, message, null, null);
	}

	public PushNotificationSendRequestImpl(PushNotificationTokenSet tokenSet,
	        String title,
	        String message,
	        String imageUrl,
	        Parameters data) {
		super(title, message, imageUrl, data);
		this.setTokenSet(tokenSet);
	}

	public PushNotificationSendRequestImpl(PushNotificationTokenSet tokenSet, PushNotificationSendRequestBody body) {
		super(body);
		this.setTokenSet(tokenSet);
	}

	// MARK: PushNotificationSendRequest
	@Override
	public PushNotificationTokenSet getTokenSet() {
		return this.tokenSet;
	}

	public void setTokenSet(PushNotificationTokenSet tokenSet) {
		if (tokenSet == null) {
			throw new IllegalArgumentException("tokenSet cannot be null.");
		}

		this.tokenSet = tokenSet;
	}

	@Override
	public String toString() {
		return "PushNotificationSendRequestImpl [tokenSet=" + this.tokenSet + ", getTitle()=" + this.getTitle()
		        + ", getMessage()=" + this.getMessage() + ", getImageUrl()=" + this.getImageUrl() + ", getData()="
		        + this.getData() + ", isContentAvailable()=" + this.isContentAvailable() + "]";
	}

}
