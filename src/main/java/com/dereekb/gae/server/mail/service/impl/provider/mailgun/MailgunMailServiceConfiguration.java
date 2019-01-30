package com.dereekb.gae.server.mail.service.impl.provider.mailgun;

/**
 * {@link MailgunMailService} configuration.
 * 
 * @author dereekb
 *
 */
public interface MailgunMailServiceConfiguration {

	/**
	 * Returns the api key.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getApiKey();

	/**
	 * Returns the domain name.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getDomain();

	/**
	 * If the service is in test mode or not.
	 * <p>
	 * Messages will not be sent.
	 * 
	 * @return {@code true} if in test mode.
	 */
	public boolean isTestMode();

}
