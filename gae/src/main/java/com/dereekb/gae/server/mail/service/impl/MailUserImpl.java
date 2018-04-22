package com.dereekb.gae.server.mail.service.impl;

import com.dereekb.gae.server.mail.service.MailUser;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link MailUser} implementation.
 *
 * @author dereekb
 *
 */
public class MailUserImpl
        implements MailUser {

	private String name;
	private String emailAddress;

	public MailUserImpl(String emailAddress, String name) {
		this(emailAddress);
		this.setName(name);
	}

	public MailUserImpl(String emailAddress) {
		super();
		this.setEmailAddress(emailAddress);
	}

	@Override
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		if (StringUtility.isEmptyString(emailAddress)) {
			throw new IllegalArgumentException("emailAddress cannot be null or empty.");
		}

		this.emailAddress = emailAddress;
	}

	@Override
	public String toString() {
		return "MailUserImpl [name=" + this.name + ", emailAddress=" + this.emailAddress + "]";
	}

}
