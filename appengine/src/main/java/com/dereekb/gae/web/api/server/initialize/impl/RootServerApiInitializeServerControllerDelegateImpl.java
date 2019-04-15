package com.dereekb.gae.web.api.server.initialize.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.AppLoginSecurityLevel;
import com.dereekb.gae.server.app.model.app.info.AppInfo;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.app.service.impl.AppLoginSecretGeneratorImpl;
import com.dereekb.gae.server.auth.security.login.LoginRegisterService;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginRegistrationRejectedException;
import com.dereekb.gae.server.auth.security.login.password.PasswordLoginPair;
import com.dereekb.gae.server.auth.security.login.password.PasswordLoginService;
import com.dereekb.gae.server.datastore.ForceGetterSetter;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.components.query.ObjectifyQueryService;
import com.dereekb.gae.server.mail.service.MailRecipient;
import com.dereekb.gae.server.mail.service.MailService;
import com.dereekb.gae.server.mail.service.MailServiceRequest;
import com.dereekb.gae.server.mail.service.MailServiceRequestBody;
import com.dereekb.gae.server.mail.service.exception.InvalidMailRequestException;
import com.dereekb.gae.server.mail.service.exception.MailSendFailureException;
import com.dereekb.gae.server.mail.service.impl.MailRecipientImpl;
import com.dereekb.gae.server.mail.service.impl.MailServiceRequestBodyImpl;
import com.dereekb.gae.server.mail.service.impl.MailServiceRequestImpl;
import com.dereekb.gae.utilities.misc.random.StringLongGenerator;
import com.dereekb.gae.web.api.auth.controller.password.impl.PasswordLoginPairImpl;

/**
 * {@link AbstractRootServerApiInitializeServerControllerDelegateImpl}
 * implementation that generates an admin login, and mails user accounts.
 *
 * @author dereekb
 *
 */
public class RootServerApiInitializeServerControllerDelegateImpl extends AbstractRootServerApiInitializeServerControllerDelegateImpl {

	private static final Logger LOGGER = Logger.getLogger(RootServerApiInitializeServerControllerDelegateImpl.class.getName());

	private String adminEmail = null;

	private MailService mailService;
	private PasswordLoginService passwordLoginService;
	private LoginRegisterService loginRegisterService;

	private Generator<String> passwordGenerator = StringLongGenerator.GENERATOR;

	private List<AppInfo> defaultSystemApps = Collections.emptyList();

	public RootServerApiInitializeServerControllerDelegateImpl(AppInfo appInfo,
	        ObjectifyRegistry<App> appRegistry) {
		this(appInfo, appRegistry, appRegistry);
	}

	public RootServerApiInitializeServerControllerDelegateImpl(AppInfo appInfo,
	        ForceGetterSetter<App> appGetterSetter,
	        ObjectifyQueryService<App> appQueryService) {
		super(appInfo, appGetterSetter, appQueryService);
	}

	public String getAdminEmail() {
		return this.adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		if (adminEmail == null) {
			throw new IllegalArgumentException("adminEmail cannot be null.");
		}

		this.adminEmail = adminEmail;
	}

	public MailService getMailService() {
		return this.mailService;
	}

	public void setMailService(MailService mailService) {
		if (mailService == null) {
			throw new IllegalArgumentException("mailService cannot be null.");
		}

		this.mailService = mailService;
	}

	public PasswordLoginService getPasswordLoginService() {
		return this.passwordLoginService;
	}

	public void setPasswordLoginService(PasswordLoginService passwordLoginService) {
		if (passwordLoginService == null) {
			throw new IllegalArgumentException("passwordLoginService cannot be null.");
		}

		this.passwordLoginService = passwordLoginService;
	}

	public LoginRegisterService getLoginRegisterService() {
		return this.loginRegisterService;
	}

	public void setLoginRegisterService(LoginRegisterService loginRegisterService) {
		if (loginRegisterService == null) {
			throw new IllegalArgumentException("loginRegisterService cannot be null.");
		}

		this.loginRegisterService = loginRegisterService;
	}

	public List<AppInfo> getDefaultSystemApps() {
		return this.defaultSystemApps;
	}

	public void setDefaultSystemApps(List<AppInfo> defaultSystemApps) {
		if (defaultSystemApps == null) {
			throw new IllegalArgumentException("defaultSystemApps cannot be null.");
		}

		this.defaultSystemApps = defaultSystemApps;
	}

	// MARK: AbstractRootServerApiInitializeServerControllerDelegateImpl
	@Override
	protected void initializeServerForFirstSetup(App app,
	                                             boolean isProduction) {
		this.makeAdminLogin();
		this.initializeDefaultApps(isProduction);
	}

	@Override
	protected App tryUpdateApp(App app) {
		return app;
	}

	// MARK: Admin
	protected Login makeAdminLogin() {
		Login login = null;

		if (this.adminEmail != null) {
			String password = this.passwordGenerator.generate();
			PasswordLoginPair passwordLoginPair = new PasswordLoginPairImpl(this.adminEmail, password);

			try {
				// Create Login
				LoginPointer pointer = this.passwordLoginService.create(passwordLoginPair, true);
				login = this.loginRegisterService.register(pointer);

				// Log Credentials
				LOGGER.log(Level.WARNING, "Created root admin: A: " + this.adminEmail + " P: " + password);

				// Send Email
				MailRecipient recipient = new MailRecipientImpl(this.adminEmail);
				MailServiceRequestBody body = new MailServiceRequestBodyImpl("Root User Created", "Created root admin: A: " + this.adminEmail + " P: " + password);
				MailServiceRequest request = new MailServiceRequestImpl(recipient, body);

				this.mailService.sendMail(request);

			} catch (LoginExistsException e) {
				throw new RuntimeException("The admin login already exists.", e);
			} catch (LoginRegistrationRejectedException e) {
				throw new RuntimeException("The admin failed to be created.", e);
			} catch (InvalidMailRequestException | MailSendFailureException e) {
				LOGGER.log(Level.WARNING, "Failed sending initial email to admin. Use console output instead.", e);
			}
		}

		return login;
	}

	// MARK: Apps
	protected void initializeDefaultApps(boolean isProduction) {
		List<App> defaultApps = this.makeDefaultApps(isProduction);

		this.getAppGetterSetter().store(defaultApps);

		// TODO: Send email to admin email address if specified, otherwise use
		// system address?
	}

	protected List<App> makeDefaultApps(boolean isProduction) {
		List<App> apps = new ArrayList<App>();

		for (AppInfo appInfo : this.defaultSystemApps) {
			App app = this.makeDefaultAppFromAppInfo(appInfo, isProduction);
			apps.add(app);
		}

		return apps;
	}

	protected App makeDefaultAppFromAppInfo(AppInfo appInfo,
	                                        boolean isProduction) {
		App app = new App();

		app.setName(appInfo.getAppName());
		app.setAppServiceVersionInfo(appInfo.getAppServiceVersionInfo());
		app.setLevel(AppLoginSecurityLevel.SYSTEM);

		if (isProduction) {
			app.setSecret(AppLoginSecretGeneratorImpl.SINGLETON.generateSecret());
		} else {
			app.setSecret(App.DEFAULT_DEVELOPMENT_SECRET);
		}

		return app;
	}

}
