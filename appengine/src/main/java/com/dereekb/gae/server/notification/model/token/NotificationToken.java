package com.dereekb.gae.server.notification.model.token;

import java.util.Date;

import com.dereekb.gae.server.notification.service.PushNotificationDevice;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.time.model.DatedModel;

/**
 * Notification token stored within {@link NotificationSettings}.
 *
 * @author dereekb
 *
 */
public class NotificationToken
        implements PushNotificationDevice, DatedModel {

	/**
	 * Device identifier/UUID.
	 */
	private String device;

	/**
	 * Notification token.
	 */
	private String token;

	/**
	 * Date the identifier was added.
	 */
	private Date date;

	public NotificationToken() {}

	public NotificationToken(PushNotificationDevice device) {
		this(device.getDevice(), device.getNotificationToken(), new Date());
	}

	public NotificationToken(String device, String token) {
		this(device, token, new Date());
	}

	public NotificationToken(String device, String token, Date date) {
		super();
		this.setDevice(device);
		this.setToken(token);
		this.setDate(date);
	}

	@Override
	public String getDevice() {
		return this.device;
	}

	public void setDevice(String device) {
		if (StringUtility.isEmptyString(device)) {
			throw new IllegalArgumentException("device cannot be null or empty.");
		}

		this.device = device;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		if (StringUtility.isEmptyString(token)) {
			throw new IllegalArgumentException("token cannot be null or empty.");
		}

		this.token = token;
	}

	@Override
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		if (date == null) {
			throw new IllegalArgumentException("date cannot be null.");
		}

		this.date = date;
	}

	// MARK: PushNotificationToken
	@Override
	public String getNotificationToken() {
		return this.token;
	}

	@Override
	public String toString() {
		return "NotificationToken [device=" + this.device + ", token=" + this.token + ", date=" + this.date + "]";
	}

}
