package com.dereekb.gae.extras.gen.app.config.project.app.context;

import java.util.List;
import java.util.Properties;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppConfigurationUtility;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.model.AppSecurityBeansConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.AppBeansConfiguration;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLListBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.server.app.model.app.info.impl.AppInfoImpl;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.app.service.impl.AppConfiguredAppLoginSecuritySigningServiceImpl;
import com.dereekb.gae.server.auth.security.app.service.impl.AppLoginSecurityDetailsServiceImpl;
import com.dereekb.gae.server.auth.security.app.service.impl.AppLoginSecurityServiceImpl;
import com.dereekb.gae.server.auth.security.app.service.impl.AppLoginSecuritySigningServiceImpl;
import com.dereekb.gae.server.auth.security.app.token.filter.LoginTokenAuthenticationFilterAppLoginSecurityVerifierImpl;
import com.dereekb.gae.server.auth.security.login.impl.LoginPointerServiceImpl;
import com.dereekb.gae.server.auth.security.login.impl.LoginRegisterServiceImpl;
import com.dereekb.gae.server.auth.security.login.impl.NewLoginGeneratorImpl;
import com.dereekb.gae.server.auth.security.login.key.impl.KeyLoginAuthenticationServiceImpl;
import com.dereekb.gae.server.auth.security.login.key.impl.KeyLoginStatusServiceManagerImpl;
import com.dereekb.gae.server.auth.security.login.oauth.impl.manager.OAuthServiceManagerImpl;
import com.dereekb.gae.server.auth.security.login.oauth.impl.service.OAuthLoginServiceImpl;
import com.dereekb.gae.server.auth.security.login.password.impl.PasswordLoginServiceImpl;
import com.dereekb.gae.server.auth.security.login.password.recover.impl.PasswordRecoveryServiceEmailDelegateImpl;
import com.dereekb.gae.server.auth.security.login.password.recover.impl.PasswordRecoveryServiceImpl;
import com.dereekb.gae.server.auth.security.login.password.recover.impl.PasswordRecoveryServiceTokenDelegateImpl;
import com.dereekb.gae.server.auth.security.misc.AccessDeniedHandlerImpl;
import com.dereekb.gae.server.auth.security.model.context.encoded.impl.LoginTokenModelContextSetEncoderDecoderImpl;
import com.dereekb.gae.server.auth.security.model.context.service.impl.LoginTokenModelContextServiceImpl;
import com.dereekb.gae.server.auth.security.model.query.task.SecurityOverrideAdminOnlyModelQueryTask;
import com.dereekb.gae.server.auth.security.model.roles.impl.CrudModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.impl.SecurityContextAnonymousModelRoleSetContextService;
import com.dereekb.gae.server.auth.security.roles.authority.impl.GrantedAuthorityDecoderImpl;
import com.dereekb.gae.server.auth.security.system.impl.SystemLoginTokenFactoryImpl;
import com.dereekb.gae.server.auth.security.token.entry.TokenAuthenticationEntryPoint;
import com.dereekb.gae.server.auth.security.token.exception.handler.ApiTokenExceptionHandler;
import com.dereekb.gae.server.auth.security.token.filter.LoginTokenAuthenticationFilter;
import com.dereekb.gae.server.auth.security.token.filter.handlers.LoginTokenAuthenticationFailureHandler;
import com.dereekb.gae.server.auth.security.token.filter.impl.LoginTokenAuthenticationFilterDelegateImpl;
import com.dereekb.gae.server.auth.security.token.gae.SignatureConfigurationFactory;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenServiceImpl;
import com.dereekb.gae.server.auth.security.token.provider.details.impl.LoginTokenGrantedAuthorityBuilderImpl;
import com.dereekb.gae.server.auth.security.token.refresh.impl.RefreshTokenEncoderDecoder;
import com.dereekb.gae.server.auth.security.token.refresh.impl.RefreshTokenServiceImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.LongModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringLongModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;
import com.dereekb.gae.server.mail.service.impl.MailUserImpl;
import com.dereekb.gae.server.mail.service.impl.provider.mailgun.impl.MailgunMailServiceConfigurationImpl;
import com.dereekb.gae.server.mail.service.impl.provider.mailgun.impl.MailgunMailServiceImpl;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskSchedulerAuthenticatorImpl;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskSchedulerImpl;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.web.matcher.MultiTypeMapAntRequestMatcher;

public class ContextServerConfigurationsGenerator extends AbstractConfigurationFileGenerator {

	public static final String SERVER_FILE_NAME = "server";
	public static final String SERVER_FOLDER_NAME = "server";

	private String serverFileName = SERVER_FILE_NAME;
	private String serverFolderName = SERVER_FOLDER_NAME;

	public ContextServerConfigurationsGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
	}

	public ContextServerConfigurationsGenerator(AppConfiguration appConfig) {
		super(appConfig);
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public GenFolder generateConfigurations() {
		GenFolderImpl folder = new GenFolderImpl(this.serverFolderName);

		// Server Files
		folder.addFile(new DatabaseConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new KeysConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new MailConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new RefConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new TaskQueueConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new SecurityConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new LoginConfigurationGenerator().generateConfigurationFile());

		// Main Server File
		folder.addFile(this.makeServerFile(folder));

		return folder;
	}

	public GenFile makeServerFile(GenFolderImpl folder) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		AppBeansConfiguration appBeans = this.getAppConfig().getAppBeans();

		builder.comment("App Info");
		builder.bean(appBeans.getAppInfoBeanId()).beanClass(AppInfoImpl.class).c().ref(appBeans.getAppKeyBeanId())
		        .ref(appBeans.getAppNameBeanId());
		builder.bean(appBeans.getAppKeyBeanId()).beanClass(ModelKey.class).c().ref(appBeans.getAppIdBeanId());
		builder.longBean(appBeans.getAppIdBeanId(), this.getAppConfig().getAppId());
		builder.stringBean(appBeans.getAppNameBeanId(), this.getAppConfig().getAppName());

		builder.comment("Import");
		builder.importResources(folder.getFiles());

		return this.makeFileWithXML(this.serverFileName, builder);
	}

	// MARK: Server Files
	public static final String OBJECTIFY_DATABASE_ENTITIES_KEY = "objectifyDatabaseEntities";

	public class DatabaseConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

		public DatabaseConfigurationGenerator() {
			super(ContextServerConfigurationsGenerator.this);
			this.setFileName("database");
		}

		@Override
		public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			builder.bean("objectifyDatabase").beanClass(ObjectifyDatabase.class).primary().lazy(false).c()
			        .ref(OBJECTIFY_DATABASE_ENTITIES_KEY);

			SpringBeansXMLListBuilder<?> entitiesList = builder.list(OBJECTIFY_DATABASE_ENTITIES_KEY);

			for (AppModelConfigurationGroup group : this.getAppConfig().getModelConfigurations()) {
				String groupName = group.getGroupName();
				entitiesList.getRawXMLBuilder().c(groupName);
				List<AppModelConfiguration> modelConfigs = group.getModelConfigurations();

				for (AppModelConfiguration modelConfig : modelConfigs) {
					if (modelConfig.isLocalModel()) {
						entitiesList.ref(modelConfig.getModelObjectifyEntryBeanId());
					}
				}
			}

			return builder;
		}

	}

	public class KeysConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

		public KeysConfigurationGenerator() {
			super(ContextServerConfigurationsGenerator.this);
			this.setFileName("key");
		}

		@Override
		public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			builder.comment("Key Converters");
			// ?

			builder.comment("Model Key Converters");
			builder.bean("longModelKeyConverter").beanClass(LongModelKeyConverterImpl.class);
			builder.bean("stringLongModelKeyConverter").beanClass(StringLongModelKeyConverterImpl.class);
			builder.bean("stringModelKeyConverter").beanClass(StringModelKeyConverterImpl.class);

			return builder;
		}

	}

	public class LoginConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

		public LoginConfigurationGenerator() {
			super(ContextServerConfigurationsGenerator.this);
			this.setFileName("login");
		}

		@Override
		public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			if (this.getAppConfig().isLoginServer()) {
				this.addLoginServerLoginTokenBeansToXMLConfigurationFile(builder);
			} else {
				this.addRemoteLoginServerLoginTokenBeansToXMLConfigurationFile(builder);
			}

			// Common Components
			this.addCommonLoginTokenBeansToXMLConfigurationFile(builder);

			return builder;
		}

		public void addLoginServerLoginTokenBeansToXMLConfigurationFile(SpringBeansXMLBuilder builder) {

			AppSecurityBeansConfigurer appSecurityBeansConfigurer = this.getAppConfig().getAppSecurityBeansConfigurer();

			builder.comment("Login Server");
			builder.comment("Signatures");

			// TODO: Update to pull signatures from JSON file or similar file
			// not included in git.

			String loginTokenSignatureFactoryId = appSecurityBeansConfigurer.getLoginTokenSignatureFactoryBeanId();
			String refreshTokenSignatureFactoryId = appSecurityBeansConfigurer.getRefreshTokenSignatureFactoryBeanId();

			builder.bean(loginTokenSignatureFactoryId).beanClass(SignatureConfigurationFactory.class).getRawXMLBuilder()
			        .comment("TODO: Add production source.");
			builder.bean(refreshTokenSignatureFactoryId).beanClass(SignatureConfigurationFactory.class)
			        .getRawXMLBuilder().comment("TODO: Add production source.");

			builder.comment("LoginToken Service");

			SpringBeansXMLBeanBuilder<?> loginTokenEncoderDecoderBuilder = builder.bean("loginTokenEncoderDecoder");
			appSecurityBeansConfigurer.configureTokenEncoderDecoder(this.getAppConfig(),
			        loginTokenEncoderDecoderBuilder);

			SpringBeansXMLBeanBuilder<?> loginTokenBuilderBuilder = builder.bean("loginTokenBuilder");
			appSecurityBeansConfigurer.configureTokenBuilder(this.getAppConfig(), loginTokenBuilderBuilder);

			builder.bean("loginTokenService").beanClass(LoginTokenServiceImpl.class).c().ref("loginTokenBuilder")
			        .ref("loginTokenEncoderDecoder");

			builder.comment("LoginPointer Service");
			builder.bean("loginPointerService").beanClass(LoginPointerServiceImpl.class).c().ref("loginPointerRegistry")
			        .ref("loginPointerScheduleCreateReview");

			builder.comment("Password Service");
			builder.bean("passwordEncoder").beanClass(BCryptPasswordEncoder.class);

			builder.bean("passwordLoginService").beanClass(PasswordLoginServiceImpl.class).c().ref("passwordEncoder")
			        .ref("loginPointerService");

			builder.bean("passwordRecoveryService").beanClass(PasswordRecoveryServiceImpl.class).c().ref("mailService")
			        .bean().beanClass(PasswordRecoveryServiceTokenDelegateImpl.class).c().ref("loginPointerService")
			        .ref(this.getAppConfig().getAppBeans().getLoginTokenServiceBeanId()).up().up().bean()
			        .beanClass(PasswordRecoveryServiceEmailDelegateImpl.class);

			builder.comment("OAuth Service");
			builder.bean("oAuthLoginService").beanClass(OAuthLoginServiceImpl.class).c().ref("loginPointerService");

			builder.bean("oAuthServiceManager").beanClass(OAuthServiceManagerImpl.class).c().ref("oAuthLoginService")
			        .map().keyType(LoginPointerType.class);

			builder.comment("KeyLogin Service");
			builder.bean("keyLoginStatusServiceManager").beanClass(KeyLoginStatusServiceManagerImpl.class).c()
			        .ref("loginPointerRegistry");

			builder.bean("keyLoginAuthenticationService").beanClass(KeyLoginAuthenticationServiceImpl.class).c()
			        .ref("loginKeyRegistry").ref("loginPointerRegistry");

			builder.comment("Register Service");
			builder.bean("loginRegisterService").beanClass(LoginRegisterServiceImpl.class).c().ref("newLoginGenerator")
			        .ref("loginRegistry").ref("loginPointerRegistry");

			builder.bean("newLoginGenerator").beanClass(NewLoginGeneratorImpl.class).c().ref("loginRegistry")
			        .ref("loginScheduleCreateReview");

			builder.comment("Refresh Token Service");
			builder.bean("refreshTokenService").beanClass(RefreshTokenServiceImpl.class).c().ref("loginRegistry")
			        .ref("loginPointerRegistry");

			builder.bean("refreshTokenEncoderDecoder").beanClass(RefreshTokenEncoderDecoder.class).c().bean()
			        .factoryBean("refreshTokenSignatureFactory").factoryMethod("make");

		}

		public void addRemoteLoginServerLoginTokenBeansToXMLConfigurationFile(SpringBeansXMLBuilder builder) {

			builder.comment("Remote Login Service");

			// TODO: For Test, use local dencoder.
			// TODO: For Dev, send requests to the dev login service.
			// TODO: For Prod, send requests to the prod login service.

			builder.comment("Register Service");

		}

		public void addCommonLoginTokenBeansToXMLConfigurationFile(SpringBeansXMLBuilder builder) {

			builder.comment("Login Token Utilities");
			builder.comment("Login Models Service");

			builder.bean("loginTokenModelContextService").beanClass(LoginTokenModelContextServiceImpl.class).c()
			        .ref("loginTokenModelContextServiceEntries");

			builder.alias("loginTokenModelContextService", "anonymousModelRoleSetContextService");

			builder.bean("loginTokenModelContextSetDencoder")
			        .beanClass(LoginTokenModelContextSetEncoderDecoderImpl.class).c()
			        .ref("loginTokenModelContextServiceDencoderEntries");

			builder.bean("securityContextAnonymousModelRoleSetContextService")
			        .beanClass(SecurityContextAnonymousModelRoleSetContextService.class);

			// Service Entries
			SpringBeansXMLListBuilder<?> entitiesList = builder.list("loginTokenModelContextServiceEntries");

			for (AppModelConfigurationGroup group : this.getAppConfig().getModelConfigurations()) {
				String groupName = group.getGroupName();
				entitiesList.getRawXMLBuilder().c(groupName);

				List<AppModelConfiguration> modelConfigs = group.getModelConfigurations();
				for (AppModelConfiguration modelConfig : modelConfigs) {

					if (modelConfig.isLocalModel()) {
						entitiesList.ref(modelConfig.getModelSecurityContextServiceEntryBeanId());
					}
				}
			}

			builder.alias("loginTokenModelContextServiceEntries", "loginTokenModelContextServiceDencoderEntries");

		}

	}

	public class MailConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

		public MailConfigurationGenerator() {
			super(ContextServerConfigurationsGenerator.this);
			this.setFileName("mail");
		}

		@Override
		public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			builder.comment("Mail");
			String mailgunMailServiceConfigurationBeanId = "mailServiceConfiguration";

			builder.bean("mailService").beanClass(MailgunMailServiceImpl.class).c().ref("serverMailUser")
			        .ref(mailgunMailServiceConfigurationBeanId);

			builder.bean("serverMailUser").beanClass(MailUserImpl.class).c().value("demo@test.com")
			        .value("test service");

			builder.bean(mailgunMailServiceConfigurationBeanId).beanClass(MailgunMailServiceConfigurationImpl.class).c()
			        .value("API_KEY").value("test.com");

			return builder;
		}

	}

	public class RefConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

		public RefConfigurationGenerator() {
			super(ContextServerConfigurationsGenerator.this);
			this.setFileName("ref");
		}

		@Override
		public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			builder.comment("Security Refs");
			for (CrudModelRole role : CrudModelRole.values()) {
				String roleString = StringUtility.firstLetterUpperCase(role.toString(), true);
				builder.bean("crud" + roleString + "ModelRole").beanClass(CrudModelRole.class).factoryMethod("valueOf")
				        .c().value(role.toString());
			}

			return builder;
		}

	}

	public class SecurityConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

		public SecurityConfigurationGenerator() {
			super(ContextServerConfigurationsGenerator.this);
			this.setFileName("security");
		}

		@Override
		public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			AppSecurityBeansConfigurer appSecurityBeansConfigurer = this.getAppConfig().getAppSecurityBeansConfigurer();

			builder.comment("TODO: Complete the security!");

			String secureModelTypesBeanId = "secureModelTypes";
			String securedModelResourcesBeanId = "securedModelResources";

			builder.comment("Patterns");

			builder.bean("securedModelResourcePatternMatcher").beanClass(MultiTypeMapAntRequestMatcher.class).c()
			        .value("/api/" + this.getAppConfig().getAppServiceName() + "/" + this.getAppConfig().getAppVersion()
			                + "/{type}/{res}")
			        .map().keyValueRefEntry("type", secureModelTypesBeanId)
			        .keyValueRefEntry("res", securedModelResourcesBeanId);

			builder.list(securedModelResourcesBeanId).values("create", "read", "update", "delete", "query", "search",
			        "link");

			builder.comment("Security");
			builder.bean("securityRequestMatcher").beanClass(AntPathRequestMatcher.class).c().value("/**").nullArg()
			        .value("false");

			builder.bean("securityEntryPoint").beanClass(TokenAuthenticationEntryPoint.class);

			builder.bean("authenticationFilter").beanClass(LoginTokenAuthenticationFilter.class).c().bean()
			        .beanClass(LoginTokenAuthenticationFilterDelegateImpl.class).c().ref("loginTokenService")
			        .ref("loginAuthenticationManager").bean()
			        .beanClass(LoginTokenAuthenticationFilterAppLoginSecurityVerifierImpl.class).c()
			        .ref("appLoginSecurityVerifierService").up().up().up().up().nullArg().bean()
			        .beanClass(LoginTokenAuthenticationFailureHandler.class).c().ref("loginTokenExceptionHandler");

			builder.getRawXMLBuilder().elem("security:authentication-manager").a("alias", "loginAuthenticationManager")
			        .e("security:authentication-provider").a("ref", "loginTokenAuthenticationProvider");

			builder.comment("Authentication");
			SpringBeansXMLBeanBuilder<?> loginTokenAuthenticationProviderBuilder = builder
			        .bean("loginTokenAuthenticationProvider");
			appSecurityBeansConfigurer.configureTokenAuthenticationProvider(this.getAppConfig(),
			        loginTokenAuthenticationProviderBuilder);

			SpringBeansXMLBeanBuilder<?> loginTokenUserDetailsBuilder = builder.bean("loginTokenUserDetailsBuilder");
			appSecurityBeansConfigurer.configureTokenUserDetailsBuilder(this.getAppConfig(),
			        loginTokenUserDetailsBuilder);

			builder.bean("loginTokenGrantedAuthorityBuilder").beanClass(LoginTokenGrantedAuthorityBuilderImpl.class).c()
			        .ref("loginGrantedAuthorityDecoder").array().bean().beanClass(SimpleGrantedAuthority.class).c()
			        .value("ROLE_USER").up().up().up().bean().beanClass(SimpleGrantedAuthority.class).c()
			        .value("ROLE_ANON");

			builder.bean("loginGrantedAuthorityDecoder").beanClass(GrantedAuthorityDecoderImpl.class)
			        .factoryMethod("withStringMap").c().map().keyType(Integer.class).valueType(String.class)
			        .value("0", "ROLE_ADMIN");

			builder.comment("Login Token System");

			String systemEncodedRolesId = "systemEncodedRoles";

			builder.bean(this.getAppConfig().getAppBeans().getSystemLoginTokenFactoryBeanId())
			        .beanClass(SystemLoginTokenFactoryImpl.class).c()
			        .ref(this.getAppConfig().getAppBeans().getAppLoginSecuritySigningServiceBeanId())
			        .ref(this.getAppConfig().getAppBeans().getLoginTokenServiceBeanId()).ref(systemEncodedRolesId);

			Long adminEncodedRole = 1L;
			builder.longBean(systemEncodedRolesId, adminEncodedRole);

			builder.comment("App Security");
			String appLoginSecurityDetailsServiceId = "appLoginSecurityDetailsService";
			String appLoginSecurityVerifierServiceId = "appLoginSecurityVerifierService";
			String appLoginSecuritySigningServiceId = this.getAppConfig().getAppBeans()
			        .getAppLoginSecuritySigningServiceBeanId();

			builder.bean(this.getAppConfig().getAppBeans().getAppLoginSecurityServiceBeanId())
			        .beanClass(AppLoginSecurityServiceImpl.class).c().bean()
			        .beanClass(AppLoginSecuritySigningServiceImpl.class).up().ref(appLoginSecurityDetailsServiceId);

			builder.alias(this.getAppConfig().getAppBeans().getAppLoginSecurityServiceBeanId(),
			        appLoginSecurityVerifierServiceId);

			builder.bean(appLoginSecurityDetailsServiceId).beanClass(AppLoginSecurityDetailsServiceImpl.class).c()
			        .ref("appRegistry");

			builder.bean(appLoginSecuritySigningServiceId)
			        .beanClass(AppConfiguredAppLoginSecuritySigningServiceImpl.class).c()
			        .ref(this.getAppConfig().getAppBeans().getAppInfoBeanId())
			        .ref(this.getAppConfig().getAppBeans().getAppLoginSecurityServiceBeanId());

			builder.comment("Secure Model Types");
			SpringBeansXMLListBuilder<?> secureModelsList = builder.list(secureModelTypesBeanId);

			for (AppModelConfiguration modelConfig : AppConfigurationUtility
			        .readModelConfigurations(this.getAppConfig())) {
				secureModelsList.ref(modelConfig.getModelTypeBeanId());
			}

			builder.comment("Exception Handler");
			builder.bean("loginTokenExceptionHandler").beanClass(ApiTokenExceptionHandler.class);
			builder.bean("accessDeniedHandler").beanClass(AccessDeniedHandlerImpl.class);

			builder.comment("Other Security");
			builder.comment("Query Overrides");
			builder.bean("securityOverrideAdminOnlyModelQueryTask")
			        .beanClass(SecurityOverrideAdminOnlyModelQueryTask.class);

			return builder;
		}

	}

	public class TaskQueueConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

		public TaskQueueConfigurationGenerator() {
			super(ContextServerConfigurationsGenerator.this);
			this.setFileName("taskqueue");
		}

		@Override
		public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			builder.comment("Task Queue");
			builder.stringBean(this.getAppConfig().getAppBeans().getTaskQueueNameId(),
			        this.getAppConfig().getAppTaskQueueName());

			builder.bean("taskScheduler").beanClass(TaskSchedulerImpl.class).c()
			        .ref(this.getAppConfig().getAppBeans().getTaskQueueNameId()).up().property("authenticator")
			        .ref("taskSchedulerAuthenticator");

			builder.bean("taskSchedulerAuthenticator").beanClass(TaskSchedulerAuthenticatorImpl.class).c()
			        .ref(this.getAppConfig().getAppBeans().getSystemLoginTokenFactoryBeanId());

			return builder;
		}

	}

}
