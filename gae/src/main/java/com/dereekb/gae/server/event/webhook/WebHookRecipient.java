package com.dereekb.gae.server.event.webhook;

/**
 * Web hook event recipient.
 *
 * @author dereekb
 *
 */
public interface WebHookRecipient {

	/**
	 * Returns the address to send the request to.
	 *
	 * @return {@link String} url for the request. Never {@code null}.
	 */
	public String getRecipientApiAddress();

}
