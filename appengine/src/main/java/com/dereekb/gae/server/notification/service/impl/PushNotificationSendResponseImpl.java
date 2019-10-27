package com.dereekb.gae.server.notification.service.impl;

import java.util.List;

import com.dereekb.gae.server.notification.service.PushNotificationSendResponse;
import com.dereekb.gae.server.notification.service.PushNotificationSendResponseTokenError;
import com.dereekb.gae.server.notification.service.PushNotificationToken;

/**
 * {@link PushNotificationSendResponse} implementation.
 *
 * @author dereekb
 *
 */
public class PushNotificationSendResponseImpl
        implements PushNotificationSendResponse {

	private List<PushNotificationToken> successfulTokens;
	private List<PushNotificationSendResponseTokenError> failedTokens;

	public PushNotificationSendResponseImpl(List<PushNotificationToken> successfulTokens2,
	        List<PushNotificationSendResponseTokenError> failedTokens) {
		super();
		this.setSuccessfulTokens(successfulTokens2);
		this.setFailedTokens(failedTokens);
	}

	// MARK: PushNotificationSendResponse
	@Override
	public List<PushNotificationToken> getSuccessfulTokens() {
		return this.successfulTokens;
	}

	public void setSuccessfulTokens(List<PushNotificationToken> successfulTokens) {
		if (successfulTokens == null) {
			throw new IllegalArgumentException("successfulTokens cannot be null.");
		}

		this.successfulTokens = successfulTokens;
	}

	@Override
	public List<PushNotificationSendResponseTokenError> getFailedTokens() {
		return this.failedTokens;
	}

	public void setFailedTokens(List<PushNotificationSendResponseTokenError> failedTokens) {
		if (failedTokens == null) {
			throw new IllegalArgumentException("failedTokens cannot be null.");
		}

		this.failedTokens = failedTokens;
	}

	@Override
	public String toString() {
		return "PushNotificationSendResponseImpl [successfulTokens=" + this.successfulTokens + ", failedTokens="
		        + this.failedTokens + "]";
	}

}
