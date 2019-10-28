package com.dereekb.gae.server.notification.model.token.dto;

import com.dereekb.gae.server.notification.model.token.NotificationToken;
import com.dereekb.gae.utilities.time.model.DatedModelData;

/**
 * DTO for {@link NotificationToken}.
 *
 * @author dereekb
 *
 */
public class NotificationTokenData extends DatedModelData {

	private String device;
	private String token;

	public String getDevice() {
		return this.device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
