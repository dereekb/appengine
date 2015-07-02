package com.dereekb.gae.server.mail;

import com.dereekb.gae.server.mail.exceptions.EmailSendFailureException;

/**
 * Manager for sending out mail.
 * 
 * @author dereekb
 */
public class MailManager {

	private MailSource source;
	private MailManagerDelegate delegate;

	public void sendMail(MailRequest request) throws EmailSendFailureException {
		if (source == null) {
			throw new EmailSendFailureException("No MailSource set.");
		}

		MailRequest finalizedRequest = request;

		if (delegate != null) {
			finalizedRequest = delegate.finalizeRequest(request);
		}

		source.sendMail(finalizedRequest);
	}

	public MailSource getSource() {
		return source;
	}

	public void setSource(MailSource source) {
		this.source = source;
	}

	public MailManagerDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(MailManagerDelegate delegate) {
		this.delegate = delegate;
	}

}
