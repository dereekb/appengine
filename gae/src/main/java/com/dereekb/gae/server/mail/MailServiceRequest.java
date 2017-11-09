package com.dereekb.gae.server.mail;

import java.util.List;
import java.util.Set;

/**
 * {@link MailService} request.
 * 
 * @author dereekb
 *
 */
public interface MailServiceRequest {

	/**
	 * Returns the sender.
	 * 
	 * @return {@link MailUser}. Never {@code null}.
	 */
	public MailUser getSender();

	/**
	 * Returns the list of recipients.
	 * 
	 * @return {@link Set}. Never {@code null} or empty.
	 */
	public Set<MailRecipient> getRecipients();

	/**
	 * Returns the body.
	 * 
	 * @return {@link MailServiceRequestBody}. Never {@code null}.
	 */
	public MailServiceRequestBody getBody();

	/**
	 * Returns the attachments.
	 * 
	 * @return {@link List}. May be {@code null}.
	 */
	public List<MailServiceRequestAttachment> getAttachments();

}
