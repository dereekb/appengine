package com.dereekb.gae.server.mail.service.impl;

import com.dereekb.gae.server.mail.service.MailUser;

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

	public MailUserImpl(String emailAddress) {
		super();
		this.setEmailAddress(emailAddress);
	}

	public MailUserImpl(String emailAddress, String name) {
		this(emailAddress);
		this.setName(name);
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
		if (emailAddress == null) {
			throw new IllegalArgumentException("emailAddress cannot be null.");
		}

		this.emailAddress = emailAddress;
	}

	@Override
	public String toString() {
		return "MailUserImpl [name=" + this.name + ", emailAddress=" + this.emailAddress + "]";
	}

}
