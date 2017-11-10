package com.dereekb.gae.server.mail.service.pairs;

import com.dereekb.gae.server.deprecated.mail.MailRequest;
import com.dereekb.gae.utilities.collections.pairs.impl.HandlerPair;

/**
 * Mail Recipient pair that contains an email address and an optional name with it.
 * 
 * @author dereekb
 * @see {@link MailRequest}
 */
public class MailRecipient extends HandlerPair<String, String> {

	public static enum RecipientType {

		/**
		 * Normal Recipient
		 */
		TO,

		/**
		 * Carbon Copy
		 */
		CC,

		/**
		 * Blind Carbon Copy
		 */
		BCC
	}

	private RecipientType recipientType;

	public MailRecipient(String address) {
		super(address, "");
	}

	public MailRecipient(String address, String name) {
		super(address, name);
	}

	public MailRecipient(String address, String name, RecipientType recipientType) {
		super(address, name);
		this.recipientType = recipientType;
	}

	public String getAddress() {
		return this.key;
	}

	public String getName() {
		return this.object;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.key == null) ? 0 : this.key.hashCode());
		return result;
	}

	public RecipientType getRecipientType() {
		return recipientType;
	}

	public void setRecipientType(RecipientType recipientType) {
		this.recipientType = recipientType;
	}

}
