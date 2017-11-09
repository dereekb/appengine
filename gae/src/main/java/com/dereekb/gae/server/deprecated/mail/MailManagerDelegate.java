package com.dereekb.gae.server.mail;

/**
 * {@link MailManager} delegate for validating and building a mail using an input request.
 * 
 * @author dereekb
 */
public interface MailManagerDelegate {

	public MailRequest finalizeRequest(MailRequest request);

}
