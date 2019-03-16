package com.dereekb.gae.server.mail.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.deprecated.mail.pairs.MailRecipient;
import com.dereekb.gae.server.storage.object.file.StorableContent;

/**
 * Mail request that contains a sender, a set of recipients, and optional
 * attachments.
 * 
 * @author dereekb
 * 
 * @see MailManager
 */
public class MailRequest {

	private MailRecipient sender;
	private Set<MailRecipient> recipients = new HashSet<MailRecipient>();

	private String subject;

	private boolean htmlContent;
	private String content;

	private List<StorableContent> attachments;

	public MailRequest() {
		super();
	}

	public MailRequest(String subject) {
		super();
		this.subject = subject;
	}

	public MailRequest(MailRequest request) {
		this.sender = request.sender;
		this.recipients = new HashSet<MailRecipient>(request.recipients);

		this.subject = request.subject;
		this.htmlContent = request.htmlContent;
		this.content = request.content;
	}

	public MailRecipient getSender() {
		return this.sender;
	}

	public void setSender(MailRecipient sender) {
		this.sender = sender;
	}

	public Set<MailRecipient> getRecipients() {
		return this.recipients;
	}

	public void setRecipients(Set<MailRecipient> recipients) throws NullPointerException {
		if (recipients == null) {
			throw new NullPointerException("Cannot set Recipients to null value.");
		}

		this.recipients = recipients;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<StorableContent> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(List<StorableContent> attachments) {
		this.attachments = attachments;
	}

	public boolean hasAttachments() {
		return ((this.attachments != null) && (this.attachments.isEmpty() == false));
	}

	public boolean isHtmlContent() {
		return this.htmlContent;
	}

	public void setHtmlContent(boolean htmlContent) {
		this.htmlContent = htmlContent;
	}

}
