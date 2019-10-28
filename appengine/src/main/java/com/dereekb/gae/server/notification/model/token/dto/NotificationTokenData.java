package com.dereekb.gae.server.notification.model.token.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.dereekb.gae.server.notification.model.token.NotificationToken;
import com.dereekb.gae.server.notification.service.PushNotificationDevice;
import com.dereekb.gae.utilities.time.model.DatedModelData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * DTO for {@link NotificationToken}.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationTokenData extends DatedModelData
        implements PushNotificationDevice {

	@NotNull
	@NotEmpty
	private String device;

	@NotNull
	@NotEmpty
	private String token;

	public NotificationTokenData() {}

	public NotificationTokenData(String device, String token, Date date) {
		super(date);
		this.setDevice(device);
		this.setToken(token);
	}

	@Override
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

	@JsonIgnore
	@Override
	public String getNotificationToken() {
		return this.token;
	}

	@Override
	public String toString() {
		return "NotificationTokenData [device=" + this.device + ", token=" + this.token + ", date=" + this.date + "]";
	}

}
