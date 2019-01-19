package com.dereekb.gae.server.mail.service.impl;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.mail.service.MailRecipient;
import com.dereekb.gae.server.mail.service.MailServiceRequest;
import com.dereekb.gae.server.mail.service.MailServiceRequestAttachment;
import com.dereekb.gae.server.mail.service.MailServiceRequestBody;
import com.dereekb.gae.server.mail.service.MailUser;
import com.dereekb.gae.utilities.collections.list.SetUtility;

/**
 * {@link MailServiceRequest} implementation.
 *
 * @author dereekb
 *
 */
public class MailServiceRequestImpl
        implements MailServiceRequest {

	private MailUser sender;
	private Set<MailRecipient> recipients;
	private MailServiceRequestBody body;
	private List<MailServiceRequestAttachment> attachments;

	public MailServiceRequestImpl() {}

	public MailServiceRequestImpl(MailRecipient recipient, MailServiceRequestBody body) {
		this(null, SetUtility.wrap(recipient), body, null);
	}

	public MailServiceRequestImpl(Set<MailRecipient> recipients, MailServiceRequestBody body) {
		this(null, recipients, body, null);
	}

	public MailServiceRequestImpl(MailUser sender,
	        Set<MailRecipient> recipients,
	        MailServiceRequestBody body,
	        List<MailServiceRequestAttachment> attachments) {
		super();
		this.setSender(sender);
		this.setRecipients(recipients);
		this.setBody(body);
		this.setAttachments(attachments);
	}

	// MARKR: MailServiceRequest
	@Override
	public MailUser getSender() {
		return this.sender;
	}

	public void setSender(MailUser sender) {
		this.sender = sender;
	}

	@Override
	public Set<MailRecipient> getRecipients() {
		return this.recipients;
	}

	public void setRecipient(MailRecipient recipient) {
		this.setRecipients(SetUtility.wrap(recipient));
	}

	public void setRecipients(Set<MailRecipient> recipients) {
		if (recipients == null || recipients.isEmpty()) {
			throw new IllegalArgumentException("recipients cannot be null or empty.");
		}

		this.recipients = recipients;
	}

	@Override
	public MailServiceRequestBody getBody() {
		return this.body;
	}

	public void setBody(MailServiceRequestBody body) {
		if (body == null) {
			throw new IllegalArgumentException("body cannot be null.");
		}

		this.body = body;
	}

	@Override
	public List<MailServiceRequestAttachment> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(List<MailServiceRequestAttachment> attachments) {
		this.attachments = attachments;
	}

	@Override
	public String toString() {
		return "MailServiceRequestImpl [sender=" + this.sender + ", recipients=" + this.recipients + ", body="
		        + this.body + ", attachments=" + this.attachments + "]";
	}

}
