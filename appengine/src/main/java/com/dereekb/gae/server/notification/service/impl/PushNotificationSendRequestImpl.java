package com.dereekb.gae.server.notification.service.impl;

import com.dereekb.gae.server.notification.service.PushNotificationSendRequest;
import com.dereekb.gae.server.notification.service.PushNotificationTokenSet;
import com.dereekb.gae.utilities.misc.parameters.Parameters;

/**
 * {@link PushNotificationSendRequest] implementation.
 *
 * @author dereekb
 *
 */
public class PushNotificationSendRequestImpl
        implements PushNotificationSendRequest {

	private PushNotificationTokenSet tokenSet;
	private String title;
	private String message;
	private String imageUrl;
	private Parameters data;

	private boolean contentAvailable = false;

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
		super();
		this.setTokenSet(tokenSet);
		this.setTitle(title);
		this.setMessage(message);
		this.setImageUrl(imageUrl);
		this.setData(data);
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
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("title cannot be null.");
		}

		this.title = title;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		if (imageUrl == null) {
			throw new IllegalArgumentException("imageUrl cannot be null.");
		}

		this.imageUrl = imageUrl;
	}

	@Override
	public Parameters getData() {
		return this.data;
	}

	public void setData(Parameters data) {
		this.data = data;
	}

	@Override
	public boolean isContentAvailable() {
		return this.contentAvailable;
	}

	public void setContentAvailable(boolean contentAvailable) {
		this.contentAvailable = contentAvailable;
	}

	@Override
	public String toString() {
		return "PushNotificationSendRequestImpl [tokenSet=" + this.tokenSet + ", title=" + this.title + ", message="
		        + this.message + ", data=" + this.data + "]";
	}

}
