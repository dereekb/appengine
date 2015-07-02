package com.dereekb.gae.server.mail.functions.observer;

import java.util.Collection;

import com.dereekb.gae.server.mail.MailRequest;

/**
 * Delegate for a {@link SendMailObserver} instance.
 * 
 * @author dereekb
 *
 */
public interface SendMailObserverDelegate<T> {

	public MailRequest createMailRequestForObjects(Collection<T> objects);

}
