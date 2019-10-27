package com.dereekb.gae.server.notification.service.impl;

import com.dereekb.gae.server.notification.service.PushNotificationSendResponseTokenError;
import com.dereekb.gae.server.notification.service.PushNotificationToken;
import com.dereekb.gae.server.notification.service.exception.PushNotificationSendException;

/**
 * {@link PushNotificationSendResponseTokenError} implementation.
 *
 * @author dereekb
 *
 */
public class PushNotificationSendResponseTokenErrorImpl
        implements PushNotificationSendResponseTokenError {

	private PushNotificationToken pushNotificationToken;
	private PushNotificationSendException sendException;

	public PushNotificationSendResponseTokenErrorImpl(PushNotificationToken pushNotificationToken,
	        PushNotificationSendException sendException) {
		super();
		this.setPushNotificationToken(pushNotificationToken);
		this.setSendException(sendException);
	}

	// MARK: PushNotificationSendResponseTokenError
	@Override
	public PushNotificationToken getPushNotificationToken() {
		return this.pushNotificationToken;
	}

	public void setPushNotificationToken(PushNotificationToken pushNotificationToken) {
		if (pushNotificationToken == null) {
			throw new IllegalArgumentException("pushNotificationToken cannot be null.");
		}

		this.pushNotificationToken = pushNotificationToken;
	}

	@Override
	public PushNotificationSendException getSendException() {
		return this.sendException;
	}

	public void setSendException(PushNotificationSendException sendException) {
		if (sendException == null) {
			throw new IllegalArgumentException("sendException cannot be null.");
		}

		this.sendException = sendException;
	}

	@Override
	public String toString() {
		return "PushNotificationSendResponseTokenErrorImpl [pushNotificationToken=" + this.pushNotificationToken
		        + ", sendException=" + this.sendException + "]";
	}

}
