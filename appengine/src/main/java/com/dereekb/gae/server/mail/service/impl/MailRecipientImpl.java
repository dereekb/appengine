package com.dereekb.gae.server.mail.service.impl;

import com.dereekb.gae.server.mail.service.MailRecipient;
import com.dereekb.gae.server.mail.service.MailRecipientType;

/**
 * {@link MailRecipient} implementation.
 * 
 * @author dereekb
 *
 */
public class MailRecipientImpl extends MailUserImpl
        implements MailRecipient {

	private MailRecipientType recipientType = MailRecipientType.TO;

	public MailRecipientImpl(String emailAddress) {
		this(emailAddress, MailRecipientType.TO);
	}

	public MailRecipientImpl(String emailAddress, MailRecipientType recipientType) {
		super(emailAddress);
		this.setRecipientType(recipientType);
	}

	@Override
	public MailRecipientType getRecipientType() {
		return this.recipientType;
	}

	public void setRecipientType(MailRecipientType recipientType) {
		if (recipientType == null) {
			throw new IllegalArgumentException("recipientType cannot be null.");
		}

		this.recipientType = recipientType;
	}

	@Override
	public String toString() {
		return "MailRecipientImpl [recipientType=" + this.recipientType + ", getName()=" + this.getName()
		        + ", getEmailAddress()=" + this.getEmailAddress() + "]";
	}

}
