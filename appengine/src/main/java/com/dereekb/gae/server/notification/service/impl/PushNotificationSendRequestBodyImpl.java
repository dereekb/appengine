package com.dereekb.gae.server.notification.service.impl;

import com.dereekb.gae.server.notification.service.PushNotificationSendRequestBody;
import com.dereekb.gae.utilities.misc.parameters.Parameters;

/**
 * {@link PushNotificationSendRequest] implementation.
 *
 * @author dereekb
 *
 */
public class PushNotificationSendRequestBodyImpl
        implements PushNotificationSendRequestBody {

	private String code;

	private String title;
	private String message;
	private String imageUrl;
	private Parameters data;

	private boolean contentAvailable = false;

	public PushNotificationSendRequestBodyImpl(String code, String title) {
		this(code, title, null, null);
	}

	public PushNotificationSendRequestBodyImpl(String code, String title, String message) {
		this(code, title, message, null);
	}

	public PushNotificationSendRequestBodyImpl(String code, String title, String message, Parameters data) {
		this(code, title, message, null, data);
	}

	public PushNotificationSendRequestBodyImpl(String code,
	        String title,
	        String message,
	        Parameters data,
	        boolean contentAvailable) {
		this(code, title, message, null, data, contentAvailable);
	}

	public PushNotificationSendRequestBodyImpl(PushNotificationSendRequestBody body) {
		this(body.getCode(), body.getTitle(), body.getMessage(), body.getImageUrl(), body.getData(),
		        body.isContentAvailable());
	}

	public PushNotificationSendRequestBodyImpl(String code,
	        String title,
	        String message,
	        String imageUrl,
	        Parameters data,
	        boolean contentAvailable) {
		this(code, title, message, imageUrl, data);
		this.setContentAvailable(contentAvailable);
	}

	public PushNotificationSendRequestBodyImpl(String code,
	        String title,
	        String message,
	        String imageUrl,
	        Parameters data) {
		super();
		this.setCode(code);
		this.setTitle(title);
		this.setMessage(message);
		this.setImageUrl(imageUrl);
		this.setData(data);
	}

	// MARK: PushNotificationSendRequest
	@Override
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
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
		return "PushNotificationSendRequestBodyImpl [title=" + this.title + ", message=" + this.message + ", data="
		        + this.data + "]";
	}

}
