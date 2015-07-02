package com.dereekb.gae.server.mail;

import java.util.Set;

import com.dereekb.gae.server.mail.exceptions.NoEmailRecipientException;
import com.dereekb.gae.server.mail.exceptions.NoEmailSenderException;
import com.dereekb.gae.server.mail.pairs.MailRecipient;

/**
 * Default implementation of the {@link MailManagerDelegate} interface.
 * 
 * Has a default sender and recipient that are put on requests that lack either.
 * 
 * @author dereekb
 */
public class DefaultMailManagerDelegate
        implements MailManagerDelegate {

	private MailRecipient defaultSender;
	private MailRecipient defaultRecipient;

	@Override
	public MailRequest finalizeRequest(MailRequest request) {
		MailRequest requestCopy = new MailRequest(request);

		this.updateSender(requestCopy);
		this.updateRecipients(requestCopy);

		return requestCopy;
	}

	private void updateSender(MailRequest request) {
		if (request.getSender() == null) {
			if (this.defaultSender == null) {
				throw new NoEmailSenderException();
			} else {
				request.setSender(defaultSender);
			}
		}
	}

	private void updateRecipients(MailRequest request) throws NoEmailRecipientException {
		Set<MailRecipient> recipients = request.getRecipients();

		if (recipients.isEmpty()) {
			if (defaultRecipient == null) {
				throw new NoEmailRecipientException();
			} else {
				recipients.add(defaultRecipient);
			}
		}
	}

}
