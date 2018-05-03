package com.dereekb.gae.extras.gen.app.config.project.app.context;

import java.util.List;
import java.util.Properties;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.services.AppSecurityBeansConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.AppRemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppConfigurationUtility;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.AppBeansConfiguration;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLListBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.extras.gen.utility.spring.security.SpringSecurityXMLHttpBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.security.impl.HasAnyRoleConfig;
import com.dereekb.gae.extras.gen.utility.spring.security.impl.HasRoleConfig;
import com.dereekb.gae.extras.gen.utility.spring.security.impl.RoleConfigImpl;
import com.dereekb.gae.server.app.model.app.info.impl.AppInfoFactoryImpl;
import com.dereekb.gae.server.app.model.app.info.impl.AppInfoImpl;
import com.dereekb.gae.server.auth.security.app.service.impl.AppConfiguredAppLoginSecuritySigningServiceImpl;
import com.dereekb.gae.server.auth.security.app.service.impl.AppLoginSecurityDetailsServiceImpl;
import com.dereekb.gae.server.auth.security.app.service.impl.AppLoginSecurityServiceImpl;
import com.dereekb.gae.server.auth.security.app.service.impl.AppLoginSecuritySigningServiceImpl;
import com.dereekb.gae.server.auth.security.app.token.filter.LoginTokenAuthenticationFilterAppLoginSecurityVerifierImpl;
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
import com.dereekb.gae.server.auth.security.token.provider.details.impl.LoginTokenGrantedAuthorityBuilderImpl;
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
import com.dereekb.gae.utilities.web.matcher.MultiRequestMatcher;
import com.dereekb.gae.utilities.web.matcher.MultiTypeAntRequestMatcher;
import com.dereekb.gae.utilities.web.matcher.MultiTypeMapAntRequestMatcher;
import com.dereekb.gae.utilities.web.matcher.method.impl.RequestMethodMatcherImpl;

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
		builder.bean("productionAppInfo").beanClass(AppInfoImpl.class).c().ref(appBeans.getAppKeyBeanId())
		        .ref(appBeans.getAppNameBeanId());

		String appInfoFactoryBeanId = "appInfoFactory";
		builder.bean(appInfoFactoryBeanId).beanClass(AppInfoFactoryImpl.class).property("productionSingleton")
		        .ref("productionAppInfo");

		builder.bean(appBeans.getAppInfoBeanId()).factoryBean(appInfoFactoryBeanId).factoryMethod("make");

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

			for (LocalModelConfigurationGroup group : this.getAppConfig().getModelConfigurations()) {
				String groupName = group.getGroupName();
				entitiesList.getRawXMLBuilder().c(groupName);
				List<LocalModelConfiguration> modelConfigs = group.getModelConfigurations();

				for (LocalModelConfiguration modelConfig : modelConfigs) {
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

			this.getAppConfig().getAppServicesConfigurer().getAppLoginTokenSecurityConfigurer()
			        .configureLoginTokenSecurityServiceComponents(this.getAppConfig(), builder);

			// Common Components
			this.addCommonLoginTokenBeansToXMLConfigurationFile(builder);

			return builder;
		}

		public void addCommonLoginTokenBeansToXMLConfigurationFile(SpringBeansXMLBuilder builder) {

			builder.comment("Login Token Utilities");
			builder.comment("Login Models Service");

			String loginTokenModelContextServiceEntriesBeanId = "loginTokenModelContextServiceEntries";

			builder.bean("loginTokenModelContextService").beanClass(LoginTokenModelContextServiceImpl.class).c()
			        .ref(loginTokenModelContextServiceEntriesBeanId);

			builder.alias("loginTokenModelContextService",
			        this.getAppConfig().getAppBeans().getAnonymousModelRoleSetContextServiceBeanId());

			builder.bean("loginTokenModelContextSetDencoder")
			        .beanClass(LoginTokenModelContextSetEncoderDecoderImpl.class).c()
			        .ref("loginTokenModelContextServiceDencoderEntries");

			builder.bean("securityContextAnonymousModelRoleSetContextService")
			        .beanClass(SecurityContextAnonymousModelRoleSetContextService.class);

			// Service Entries
			SpringBeansXMLListBuilder<?> entitiesList = builder.list(loginTokenModelContextServiceEntriesBeanId);

			// Local Types
			for (LocalModelConfigurationGroup group : this.getAppConfig().getLocalModelConfigurations()) {
				String groupName = group.getGroupName();
				entitiesList.getRawXMLBuilder().c(groupName);

				List<LocalModelConfiguration> modelConfigs = group.getModelConfigurations();
				for (LocalModelConfiguration modelConfig : modelConfigs) {

					if (modelConfig.isLocalModel()) {
						entitiesList.ref(modelConfig.getModelSecurityContextServiceEntryBeanId());
					}
				}
			}

			// Remote Types
			for (AppRemoteServiceConfiguration remoteService : this.getAppConfig().getRemoteServices()) {
				// TODO: Add ModelRoleSet retrieval pieces here.
			}

			builder.alias(loginTokenModelContextServiceEntriesBeanId, "loginTokenModelContextServiceDencoderEntries");
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

			String secureModelTypesBeanId = "secureModelTypes";
			String securedModelResourcesBeanId = "securedModelResources";

			String securedModelPatternMatcherBeanId = "securedModelPatternMatcher";
			String securedModelReadPatternMatcherBeanId = "securedModelReadPatternMatcher";
			String securedModelResourcePatternMatcherBeanId = "securedModelResourcePatternMatcher";

			builder.comment("No HTTP Security For Google App Engine Test Server");
			builder.httpSecurity().pattern("/_ah/**").security("none");

			// TODO: Add custom pattern matching components.

			if (this.getAppConfig().isLoginServer()) {
				this.addLoginServerAuthenticationSecurity(builder);
			}

			builder.comment("Protected Application Resources");
			SpringSecurityXMLHttpBeanBuilder<?> http = builder.httpSecurity().useExpressions().stateless()
			        .entryPointRef("securityEntryPoint");

			http.filter("authenticationFilter", "PRE_AUTH_FILTER");

			http.getRawXMLBuilder().c("Only allow this service to access the taskqueue.");
			http.intercept("/taskqueue/**", HasRoleConfig.make("ROLE_LOGINTYPE_SYSTEM"));

			String serviceApiPath = this.getServiceApiPath();

			if (this.getAppConfig().isLoginServer()) {
				http.getRawXMLBuilder().c("LoginKey Auth Requests rejected for some roles.");
				http.intercept(serviceApiPath + "/login/auth/key/*",
				        HasAnyRoleConfig.not("ROLE_LOGINTYPE_API", "ROLE_ANON", "ROLE_SYSTEM"));

				http.getRawXMLBuilder().c("LoginKey Requests rejected for some roles.");
				http.intercept().matcherRef("loginKeyObjectApiPathRequestMatcher")
				        .access(HasAnyRoleConfig.not("ROLE_LOGINTYPE_API", "ROLE_ANON"));
			}

			http.getRawXMLBuilder().c("Secured Owned Model Patterns");
			http.intercept().matcherRef(securedModelPatternMatcherBeanId).access(HasRoleConfig.make("ROLE_USER"));

			http.getRawXMLBuilder().c("Other Extension Resources");
			http.intercept(serviceApiPath + "/search/**", HasRoleConfig.make("ROLE_ADMIN"));

			if (this.getAppConfig().isLoginServer()) {
				http.getRawXMLBuilder().c("Register Patterns");
				http.intercept(serviceApiPath + "/login/auth/register", HasRoleConfig.make("ROLE_NEW_USER"),
				        HttpMethod.POST);
				http.intercept(serviceApiPath + "/login/auth/register/token", HasRoleConfig.make("ROLE_USER"),
				        HttpMethod.POST);

				http.getRawXMLBuilder().c("Taken Patterns");
				http.intercept(serviceApiPath + "/login/auth/model/*", HasRoleConfig.make("ROLE_USER"), HttpMethod.PUT);
				http.intercept(serviceApiPath + "/login/auth/token/refresh", HasRoleConfig.make("ROLE_USER"),
				        HttpMethod.GET);
				http.intercept(serviceApiPath + "/login/auth/token/reset", HasRoleConfig.make("ROLE_USER"),
				        HttpMethod.POST);
				http.intercept(serviceApiPath + "/login/auth/token/reset/*", HasRoleConfig.make("ROLE_ADMIN"),
				        HttpMethod.POST);
			}

			http.getRawXMLBuilder().c("Scheduling Pattern");
			http.intercept(serviceApiPath + "/scheduler/schedule", HasRoleConfig.make("ROLE_USER"), HttpMethod.POST);

			http.getRawXMLBuilder().c("Everything Else Is Denied");
			http.intercept("/**", RoleConfigImpl.makel("denyAll"));

			http.accessDeniedHandlerRef("accessDeniedHandler");

			http.getRawXMLBuilder().c("No Anonymous Allowed");
			http.noAnonymous().noCsrf();

			if (this.getAppConfig().isLoginServer()) {
				builder.bean("loginKeyObjectApiPathRequestMatcher").beanClass(AntPathRequestMatcher.class).c()
				        .value(serviceApiPath + "/loginkey/**").nullArg().value("false");
			}

			// Security
			builder.comment("Secure Model Pattern Matchers");
			builder.bean(securedModelPatternMatcherBeanId).beanClass(MultiRequestMatcher.class).c().list()
			        .ref(securedModelReadPatternMatcherBeanId).ref(securedModelResourcePatternMatcherBeanId);

			builder.bean(securedModelReadPatternMatcherBeanId).beanClass(MultiTypeAntRequestMatcher.class).c()
			        .value(serviceApiPath + "/{type}").ref(secureModelTypesBeanId).up().property("methodMatcher").bean()
			        .beanClass(RequestMethodMatcherImpl.class).c().value("GET");

			builder.bean(securedModelResourcePatternMatcherBeanId).beanClass(MultiTypeMapAntRequestMatcher.class).c()
			        .value(serviceApiPath + "/{type}/{res}").map().keyValueRefEntry("type", secureModelTypesBeanId)
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

			for (LocalModelConfiguration modelConfig : AppConfigurationUtility
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

		/**
		 * Adds security components for accessing the
		 */
		private void addLoginServerAuthenticationSecurity(SpringBeansXMLBuilder builder) {

			SpringSecurityXMLHttpBeanBuilder<?> http = builder.httpSecurity()
			        .requestMatcherRef("authControllersPatternMatcher").stateless().entryPointRef("securityEntryPoint");

			String serviceApiPath = this.getServiceApiPath();

			http.getRawXMLBuilder().c("Only allow this service to access the taskqueue.");
			http.getRawXMLBuilder().c("Authentication Matched");
			http.intercept(serviceApiPath + "/login/auth/pass", RoleConfigImpl.makel("permitAll"), HttpMethod.POST);
			http.intercept(serviceApiPath + "/login/auth/pass/**", RoleConfigImpl.makel("permitAll"), HttpMethod.POST);
			http.intercept(serviceApiPath + "/login/auth/oauth/**", RoleConfigImpl.makel("permitAll"), HttpMethod.POST);
			http.intercept(serviceApiPath + "/login/auth/key", RoleConfigImpl.makel("permitAll"), HttpMethod.POST);

			http.getRawXMLBuilder().c("Token Matched");
			http.intercept(serviceApiPath + "/login/auth/token/**", RoleConfigImpl.makel("permitAll"), HttpMethod.POST);
			http.intercept(serviceApiPath + "/**", RoleConfigImpl.makel("denyAll"));

			http.accessDeniedHandlerRef("accessDeniedHandler").anonymous(true).noCsrf();

			// Pattern Matchers
			String authControllersMainPatternMatcherBeanId = "authControllersMainPatternMatcher";
			String authControllersTokenPatternMatcherBeanId = "authControllersTokenPatternMatcher";

			http.getRawXMLBuilder()
			        .c("Matches POST requests made to the Auth Controllers. Everything else falls through.");
			builder.bean("authControllersPatternMatcher").beanClass(MultiRequestMatcher.class).c().list()
			        .ref(authControllersMainPatternMatcherBeanId).ref(authControllersTokenPatternMatcherBeanId);

			builder.bean("authControllersMainPatternMatcher").beanClass(MultiTypeAntRequestMatcher.class).c()
			        .value(serviceApiPath + "/login/auth/{type}/**").list().value("pass").value("oauth").value("key")
			        .up().up().property("methodMatcher").bean().beanClass(RequestMethodMatcherImpl.class).c()
			        .value("POST");

			builder.bean("authControllersTokenPatternMatcher").beanClass(MultiTypeAntRequestMatcher.class).c()
			        .value(serviceApiPath + "/login/auth/token/{type}").list().value("login").value("refresh")
			        .value("validate").up().up().property("methodMatcher").bean()
			        .beanClass(RequestMethodMatcherImpl.class).c().value("POST");

		}

		private String getServiceApiPath() {
			return this.getAppConfig().getAppServiceConfigurationInfo().getRootAppApiPath();
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
