package com.dereekb.gae.server.initialize.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.AppLoginSecurityLevel;
import com.dereekb.gae.server.app.model.app.info.SystemAppInfo;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.LoginRegisterService;
import com.dereekb.gae.server.auth.security.login.LoginRolesService;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginRegistrationRejectedException;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;
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
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.misc.random.StringLongGenerator;
import com.dereekb.gae.web.api.auth.controller.password.impl.PasswordLoginPairImpl;

/**
 * {@link AbstractRootServerInitializeService}
 * implementation that generates an admin login, and mails user accounts.
 * <p>
 * To be executed on the root server.
 *
 * @author dereekb
 *
 */
public class RootServerInitializeService extends AbstractRootServerInitializeService {

	private static final Logger LOGGER = Logger.getLogger(RootServerInitializeService.class.getName());
	private static final String DEFAULT_DEVELOPMENT_EMAIL = "test@test.com";

	private String adminEmail = null;
	private String developmentEmail = DEFAULT_DEVELOPMENT_EMAIL;

	/**
	 * Whether or not to generate the non-admin users in the production
	 * environment.
	 */
	private boolean generateUserLoginsInProduction = false;

	private MailService mailService;
	private PasswordLoginService passwordLoginService;
	private LoginRegisterService loginRegisterService;
	private LoginRolesService loginRolesService;

	private Generator<String> passwordGenerator = StringLongGenerator.GENERATOR;

	private List<SystemAppInfo> defaultSystemApps = Collections.emptyList();

	public RootServerInitializeService(SystemAppInfo appInfo, ObjectifyRegistry<App> appRegistry) {
		this(appInfo, appRegistry, appRegistry);
	}

	public RootServerInitializeService(SystemAppInfo appInfo,
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

	public String getDevelopmentEmail() {
		return this.developmentEmail;
	}

	public void setDevelopmentEmail(String developmentEmail) {
		if (developmentEmail == null) {
			throw new IllegalArgumentException("developmentEmail cannot be null.");
		}

		this.developmentEmail = developmentEmail;
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

	public boolean isGenerateUserLoginsInProduction() {
		return this.generateUserLoginsInProduction;
	}

	public void setGenerateUserLoginsInProduction(boolean generateUserLoginsInProduction) {
		this.generateUserLoginsInProduction = generateUserLoginsInProduction;
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

	public LoginRolesService getLoginRolesService() {
		return this.loginRolesService;
	}

	public void setLoginRolesService(LoginRolesService loginRolesService) {
		if (loginRolesService == null) {
			throw new IllegalArgumentException("loginRolesService cannot be null.");
		}

		this.loginRolesService = loginRolesService;
	}

	public Generator<String> getPasswordGenerator() {
		return this.passwordGenerator;
	}

	public void setPasswordGenerator(Generator<String> passwordGenerator) {
		if (passwordGenerator == null) {
			throw new IllegalArgumentException("passwordGenerator cannot be null.");
		}

		this.passwordGenerator = passwordGenerator;
	}

	public List<SystemAppInfo> getDefaultSystemApps() {
		return this.defaultSystemApps;
	}

	public void setDefaultSystemApps(List<SystemAppInfo> defaultSystemApps) {
		if (defaultSystemApps == null) {
			throw new IllegalArgumentException("defaultSystemApps cannot be null.");
		}

		this.defaultSystemApps = defaultSystemApps;
	}

	// MARK: AbstractRootServerApiInitializeServerControllerDelegateImpl
	@Override
	protected void initializeServerForFirstSetup(App app,
	                                             boolean isProduction) {
		this.makeInitialUserLogins(isProduction);
		this.initializeDefaultApps(isProduction);
	}

	@Override
	protected App tryUpdateApp(App app) {
		return app;
	}

	// MARK: Logins
	protected Collection<Login> makeInitialUserLogins(boolean isProduction) {
		Collection<Login> logins = new ArrayList<Login>();

		Login admin = this.makeAdminLogin(isProduction);
		ListUtility.addElements(logins, admin);

		if (this.generateUserLoginsInProduction && isProduction) {
			Collection<Login> developmentLogins = this.makeUserLogins(isProduction);
			ListUtility.addElements(logins, developmentLogins);
		}

		return logins;
	}

	protected Login makeAdminLogin(boolean isProduction) {
		Login login = null;

		if (this.adminEmail != null) {
			String password = this.makeAdminPassword(isProduction);
			login = this.makeAdminLogin(this.adminEmail, password);
		}

		return login;
	}

	protected String makeAdminPassword(boolean isProduction) {
		if (isProduction) {
			return this.passwordGenerator.generate();
		} else {
			return LoginPointer.DEFAULT_DEVELOPMENT_PASSWORD;
		}
	}

	protected Login makeAdminLogin(String email,
	                               String password) {
		Login login = null;

		PasswordLoginPair passwordLoginPair = new PasswordLoginPairImpl(email, password);

		try {
			// Create Login
			LoginPointer pointer = this.passwordLoginService.create(passwordLoginPair, true);
			login = this.loginRegisterService.register(pointer);
			login = this.loginRolesService.makeAdmin(login.getModelKey());

			// Log Credentials
			LOGGER.log(Level.WARNING, "Created root admin: A: " + email + " P: " + password + " - CHANGE ASAP!");

			// Send Email
			MailRecipient recipient = new MailRecipientImpl(email);
			MailServiceRequestBody body = new MailServiceRequestBodyImpl("Root User Created",
			        "Created root admin: A: " + email + " P: " + password);
			MailServiceRequest request = new MailServiceRequestImpl(recipient, body);

			this.mailService.sendMail(request);

		} catch (LoginExistsException e) {
			throw new RuntimeException("The admin login already exists.", e);
		} catch (LoginRegistrationRejectedException e) {
			throw new RuntimeException("The admin failed to be created.", e);
		} catch (InvalidMailRequestException | MailSendFailureException e) {
			LOGGER.log(Level.WARNING, "Failed sending initial email to admin. Use console output instead.", e);
		} catch (LoginUnavailableException e) {
			throw new RuntimeException("The admin was unavailable for becoming an admin.", e);
		}

		return login;
	}

	protected Collection<Login> makeUserLogins(boolean isProduction) {
		Collection<Login> logins = new ArrayList<Login>();

		Login login = this.makeUserLogin(this.developmentEmail, this.makeUserPassword(isProduction));
		ListUtility.addElements(logins, login);

		return logins;
	}

	protected String makeUserPassword(boolean isProduction) {
		return LoginPointer.DEFAULT_DEVELOPMENT_PASSWORD;
	}

	protected Login makeUserLogin(String email,
	                              String password) {
		Login login = null;

		PasswordLoginPair passwordLoginPair = new PasswordLoginPairImpl(email, password);

		try {
			// Create Login
			LoginPointer pointer = this.passwordLoginService.create(passwordLoginPair, true);
			login = this.loginRegisterService.register(pointer);

			// Log Credentials
			LOGGER.log(Level.WARNING, "Created user account: A: " + email + " P: " + password);

		} catch (LoginExistsException e) {
			throw new RuntimeException("The admin login already exists.", e);
		} catch (LoginRegistrationRejectedException e) {
			throw new RuntimeException("The admin failed to be created.", e);
		}

		return login;
	}

	// MARK: Apps
	protected void initializeDefaultApps(boolean isProduction) {
		List<App> defaultApps = this.makeDefaultApps(isProduction);
		this.getAppGetterSetter().store(defaultApps);
	}

	protected List<App> makeDefaultApps(boolean isProduction) {
		List<App> apps = new ArrayList<App>();

		for (SystemAppInfo appInfo : this.defaultSystemApps) {
			App app = this.makeDefaultAppFromAppInfo(appInfo, isProduction);
			apps.add(app);
		}

		return apps;
	}

	protected App makeDefaultAppFromAppInfo(SystemAppInfo appInfo,
	                                        boolean isProduction) {
		App app = new App();

		app.setName(appInfo.getAppName());
		app.setSystemKey(appInfo.getSystemKey());
		app.setAppServiceVersionInfo(appInfo.getAppServiceVersionInfo());
		app.setLevel(AppLoginSecurityLevel.SYSTEM);

		String secret = this.makeAppSecret(isProduction);
		app.setSecret(secret);

		return app;
	}

	protected String makeAppSecret(boolean isProduction) {
		if (isProduction) {
			return this.getAppLoginSecretGenerator().generateSecret();
		} else {
			return App.DEFAULT_DEVELOPMENT_SECRET;
		}
	}

}
