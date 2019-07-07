package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppMailServiceConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.mail.service.MailUser;
import com.dereekb.gae.server.mail.service.impl.MailUserImpl;
import com.dereekb.gae.server.mail.service.impl.provider.mailgun.impl.MailgunMailServiceConfigurationImpl;
import com.dereekb.gae.server.mail.service.impl.provider.mailgun.impl.MailgunMailServiceImpl;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.misc.env.EnvStringFactory;

/**
 * {@link AppMailServiceConfigurer} implementation for Mailgun.
 *
 * @author dereekb
 *
 */
public class MailgunAppMailServiceConfigurerImpl
        implements AppMailServiceConfigurer {

	public static final String API_KEY_ENV_VAR = "MAILGUN_API_KEY";
	private static final String MISSING_API_KEY_STRING = "MISSING_API_KEY";

	private MailUser mailUser;
	private String domain;

	public MailgunAppMailServiceConfigurerImpl(MailUser mailUser) {
		this(mailUser, null);
	}

	public MailgunAppMailServiceConfigurerImpl(MailUser mailUser, String domain) {
		super();
		this.setMailUser(mailUser);
		this.setDomain(domain);
	}

	public MailUser getMailUser() {
		return this.mailUser;
	}

	public void setMailUser(MailUser mailUser) {
		if (mailUser == null) {
			throw new IllegalArgumentException("mailUser cannot be null.");
		}

		this.mailUser = mailUser;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	// MARK: AppMailServiceConfigurer
	@Override
	public void configureMailService(AppConfiguration appConfig,
	                                 SpringBeansXMLBuilder builder) {

		builder.comment("Mail");
		String mailServiceBeanId = appConfig.getAppBeans().getMailServiceBeanId();
		String serverMailUserBeanId = "serverMailUser";
		String mailgunMailServiceConfigurationBeanId = "mailServiceConfiguration";

		builder.bean(mailServiceBeanId).beanClass(MailgunMailServiceImpl.class).c().ref(serverMailUserBeanId)
		        .ref(mailgunMailServiceConfigurationBeanId);

		builder.bean(serverMailUserBeanId).beanClass(MailUserImpl.class).c().value(this.mailUser.getEmailAddress())
		        .value(this.mailUser.getName());

		EnvStringFactory apiKeyFactory = new EnvStringFactory(MailgunAppMailServiceConfigurerImpl.API_KEY_ENV_VAR,
		        MailgunAppMailServiceConfigurerImpl.MISSING_API_KEY_STRING);

		String apiKey = apiKeyFactory.make();

		String domain = this.getDomain();

		if (StringUtility.isEmptyString(domain)) {
			domain = appConfig.getAppDomain();
		}

		builder.bean(mailgunMailServiceConfigurationBeanId).beanClass(MailgunMailServiceConfigurationImpl.class).c()
		        .value(apiKey).value(domain);
	}

}
