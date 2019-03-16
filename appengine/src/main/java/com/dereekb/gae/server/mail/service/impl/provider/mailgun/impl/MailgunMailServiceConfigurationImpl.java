package com.dereekb.gae.server.mail.service.impl.provider.mailgun.impl;

import com.dereekb.gae.server.mail.service.impl.provider.mailgun.MailgunMailServiceConfiguration;

public class MailgunMailServiceConfigurationImpl
        implements MailgunMailServiceConfiguration {

	private String apiKey;
	private String domain;
	private boolean testMode = false;

	public MailgunMailServiceConfigurationImpl(String apiKey, String domain) {
		super();
		this.setApiKey(apiKey);
		this.setDomain(domain);
	}

	@Override
	public String getApiKey() {
		return this.apiKey;
	}

	public void setApiKey(String apiKey) {
		if (apiKey == null) {
			throw new IllegalArgumentException("apiKey cannot be null.");
		}

		this.apiKey = apiKey;
	}

	@Override
	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		if (domain == null) {
			throw new IllegalArgumentException("domain cannot be null.");
		}

		this.domain = domain;
	}

	@Override
	public String toString() {
		return "MailgunMailServiceConfigurationImpl [apiKey=" + this.apiKey + ", domain=" + this.domain + "]";
	}

	public boolean isTestMode() {
		return this.testMode;
	}

	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
	}

}
