package com.dereekb.gae.extras.gen.app.config.project.app.context;

import java.util.List;
import java.util.Properties;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dereekb.gae.client.api.auth.model.security.ClientLoginTokenModelContextServiceEntryFactory;
import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.services.AppSecurityBeansConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServerInitializationConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
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
import com.dereekb.gae.server.app.model.app.info.impl.AppServiceVersionInfoImpl;
import com.dereekb.gae.server.auth.security.app.token.filter.LoginTokenAuthenticationFilterAppLoginSecurityVerifierImpl;
import com.dereekb.gae.server.auth.security.misc.AccessDeniedHandlerImpl;
import com.dereekb.gae.server.auth.security.model.context.encoded.impl.LoginTokenModelContextSetEncoderDecoderImpl;
import com.dereekb.gae.server.auth.security.model.context.service.impl.LoginTokenModelContextServiceImpl;
import com.dereekb.gae.server.auth.security.model.query.task.impl.AdminOnlySecurityModelQueryTask;
import com.dereekb.gae.server.auth.security.model.query.task.impl.AllowAllSecurityModelQueryTask;
import com.dereekb.gae.server.auth.security.model.roles.impl.CrudModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.impl.SecurityContextAnonymousModelRoleSetContextService;
import com.dereekb.gae.server.auth.security.roles.authority.impl.GrantedAuthorityDecoderImpl;
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
import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseImpl;
import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyInitializerImpl;
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

/**
 * Used for generating all server-specific shared context configurations within
 * the application.
 * <p>
 * Configurations are generally output to the "server" folder.
 *
 * @author dereekb
 *
 */
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
		folder.addFile(new StartupConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new DatabaseConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new KeysConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new MailConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new RefConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new TaskQueueConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new SecurityConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new LoginConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new UtilityConfigurationGenerator().generateConfigurationFile());

		// Main Server File
		folder.addFile(this.makeServerFile(folder));

		return folder;
	}

	public GenFile makeServerFile(GenFolderImpl folder) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		AppBeansConfiguration appBeans = this.getAppConfig().getAppBeans();

		builder.comment("App Info");

		String productionAppServiceInfoBeanId = "productionAppServiceInfo";

		builder.bean(productionAppServiceInfoBeanId).beanClass(AppServiceVersionInfoImpl.class).c()
		        .value(this.getAppConfig().getAppServiceConfigurationInfo().getAppProjectId())
		        .value(this.getAppConfig().getAppServiceConfigurationInfo().getAppServiceName())
		        .value(this.getAppConfig().getAppServiceConfigurationInfo().getAppVersion());

		builder.bean("productionAppInfo").beanClass(AppInfoImpl.class).c().ref(appBeans.getAppKeyBeanId())
		        .ref(appBeans.getAppNameBeanId()).ref(productionAppServiceInfoBeanId);

		String appInfoFactoryBeanId = "appInfoFactory";
		builder.bean(appInfoFactoryBeanId).beanClass(AppInfoFactoryImpl.class).property("productionSingleton")
		        .ref("productionAppInfo");

		builder.bean(appBeans.getAppInfoBeanId()).factoryBean(appInfoFactoryBeanId).factoryMethod("make");

		builder.bean(appBeans.getAppKeyBeanId()).beanClass(ModelKey.class).c().ref(appBeans.getAppIdBeanId());
		builder.longBean(appBeans.getAppIdBeanId(), this.getAppConfig().getAppId());
		builder.stringBean(appBeans.getAppNameBeanId(), this.getAppConfig().getAppName());
		builder.stringBean(appBeans.getAppSecretBeanId(), this.getAppConfig().getAppSecret());

		builder.comment("Development");
		builder.stringBean(appBeans.getAppDevelopmentProxyUrlBeanId(), this.getAppConfig().getAppDevelopmentProxyUrl());

		builder.comment("Import");
		builder.importResources(folder.getFiles());

		return this.makeFileWithXML(this.serverFileName, builder);
	}

	// MARK: Server Files
	public static final String OBJECTIFY_DATABASE_ENTITIES_KEY = "objectifyDatabaseEntities";

	public class StartupConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

		public StartupConfigurationGenerator() {
			super(ContextServerConfigurationsGenerator.this);
			this.setFileName("startup");
		}

		@Override
		public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			AppConfiguration appConfig = this.getAppConfig();
			AppServerInitializationConfigurer configurer = appConfig.getAppServicesConfigurer().getAppServerInitializationConfigurer();
			configurer.configureContextInitializationComponents(appConfig, builder);

			return builder;
		}

	}

	public class DatabaseConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

		public DatabaseConfigurationGenerator() {
			super(ContextServerConfigurationsGenerator.this);
			this.setFileName("database");
		}

		@Override
		public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			String objectifyInitializerBean = this.getAppConfig().getAppBeans().getObjectifyInitializerId();

			builder.bean(objectifyInitializerBean).beanClass(ObjectifyInitializerImpl.class).lazy(false);

			builder.bean(this.getAppConfig().getAppBeans().getObjectifyDatabaseId())
			        .beanClass(ObjectifyDatabaseImpl.class).lazy(false).c().ref(objectifyInitializerBean)
			        .ref(OBJECTIFY_DATABASE_ENTITIES_KEY);

			SpringBeansXMLListBuilder<?> entitiesList = builder.list(OBJECTIFY_DATABASE_ENTITIES_KEY);

			for (LocalModelConfigurationGroup group : this.getAppConfig().getLocalModelConfigurations()) {
				String groupName = group.getGroupName();
				entitiesList.getRawXMLBuilder().c(groupName);
				List<LocalModelConfiguration> modelConfigs = group.getModelConfigurations();

				for (LocalModelConfiguration modelConfig : modelConfigs) {
					entitiesList.ref(modelConfig.getModelObjectifyEntryBeanId());
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
			/*
			 * These are configured within the
			 * SecurityExtensionConfigurationGenerator in
			 * ContextModelsConfigurationGenerator.
			 */
			for (LocalModelConfigurationGroup group : this.getAppConfig().getLocalModelConfigurations()) {
				String groupName = group.getGroupName();
				entitiesList.getRawXMLBuilder().c(groupName);

				List<LocalModelConfiguration> modelConfigs = group.getModelConfigurations();
				for (LocalModelConfiguration modelConfig : modelConfigs) {
					entitiesList.ref(modelConfig.getModelSecurityContextServiceEntryBeanId());
				}
			}

			// Remote Types
			entitiesList.getRawXMLBuilder().c("Remote");
			for (RemoteServiceConfiguration remoteService : this.getAppConfig().getRemoteServices()) {
				entitiesList.getRawXMLBuilder()
				        .c(remoteService.getAppServiceConfigurationInfo().getAppServiceName() + " Service");
				for (RemoteModelConfigurationGroup group : remoteService.getServiceModelConfigurations()) {
					String groupName = group.getGroupName();
					entitiesList.getRawXMLBuilder().c(groupName);

					List<RemoteModelConfiguration> modelConfigs = group.getModelConfigurations();
					for (RemoteModelConfiguration modelConfig : modelConfigs) {
						entitiesList.ref(modelConfig.getModelSecurityContextServiceEntryBeanId());
					}
				}
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

	public class UtilityConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

		public UtilityConfigurationGenerator() {
			super(ContextServerConfigurationsGenerator.this);
			this.setFileName("util");
		}

		@Override
		public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			if (this.getAppConfig().hasRemoteServices()) {
				this.makeRemoteUtilityBeans(builder);
			}

			return builder;
		}

		protected void makeRemoteUtilityBeans(SpringBeansXMLBuilder builder) {
			builder.comment("Remote Utilities");

			AppConfiguration appConfig = this.getAppConfig();

			// MARK: Remote Utilities
			builder.comment("Remote Service Security");
			builder.bean(appConfig.getAppBeans().getUtilityBeans()
			        .getClientLoginTokenModelContextServiceEntryFactoryBeanId())
			        .beanClass(ClientLoginTokenModelContextServiceEntryFactory.class).c()
			        .ref(appConfig.getAppBeans().getModelKeyTypeConverterId());
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

			String serviceApiPath = this.getServiceApiPath();

			List<LocalModelConfiguration> secureLocalModelConfigs = AppConfigurationUtility
			        .readLocalModelConfigurations(this.getAppConfig());
			boolean hasSecureLocalModelConfigs = (secureLocalModelConfigs.isEmpty() == false);

			builder.comment("No HTTP Security For Google App Engine Test Server");
			builder.httpSecurity().pattern("/_ah/**").security("none");

			builder.comment("Allow anyone to initialize the server via GET.");
			builder.httpSecurity().pattern(serviceApiPath + "/server/initialize").security("none");

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

			if (this.getAppConfig().isLoginServer()) {
				http.getRawXMLBuilder().c("LoginKey Auth Requests rejected for some roles.");
				http.intercept(serviceApiPath + "/login/auth/key/*",
				        HasAnyRoleConfig.not("ROLE_LOGINTYPE_API", "ROLE_ANON", "ROLE_SYSTEM"));

				http.getRawXMLBuilder().c("LoginKey Requests rejected for some roles.");
				http.intercept().matcherRef("loginKeyObjectApiPathRequestMatcher")
				        .access(HasAnyRoleConfig.not("ROLE_LOGINTYPE_API", "ROLE_ANON"));
			}

			if (hasSecureLocalModelConfigs) {
				http.getRawXMLBuilder().c("Secured Owned Model Patterns");
				http.intercept().matcherRef(securedModelPatternMatcherBeanId).access(HasRoleConfig.make("ROLE_USER"));
			}

			http.getRawXMLBuilder().c("Other Extension Resources");
			http.intercept(serviceApiPath + "/search/**", HasRoleConfig.make("ROLE_ADMIN"));

			// Login Registration Patterns
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

			// Server Scheduler Routes
			http.getRawXMLBuilder().c("Scheduling Pattern");
			http.intercept(serviceApiPath + "/scheduler/schedule", HasRoleConfig.make("ROLE_USER"), HttpMethod.POST);

			http.getRawXMLBuilder().c("Everything Else Is Denied");
			http.intercept("/**", RoleConfigImpl.make("denyAll"));

			http.accessDeniedHandlerRef("accessDeniedHandler");

			http.getRawXMLBuilder().c("No Anonymous Allowed");
			http.noAnonymous().noCsrf();

			if (this.getAppConfig().isLoginServer()) {
				builder.bean("loginKeyObjectApiPathRequestMatcher").beanClass(AntPathRequestMatcher.class).c()
				        .value(serviceApiPath + "/loginkey/**").nullArg().value("false");
			}

			// Security
			builder.comment("Secure Model Pattern Matchers");

			if (hasSecureLocalModelConfigs) {
				builder.bean(securedModelPatternMatcherBeanId).beanClass(MultiRequestMatcher.class).c().list()
				        .ref(securedModelReadPatternMatcherBeanId).ref(securedModelResourcePatternMatcherBeanId);

				builder.bean(securedModelReadPatternMatcherBeanId).beanClass(MultiTypeAntRequestMatcher.class).c()
				        .value(serviceApiPath + "/{type}").ref(secureModelTypesBeanId).up().property("methodMatcher")
				        .bean().beanClass(RequestMethodMatcherImpl.class).c().value("GET");

				builder.bean(securedModelResourcePatternMatcherBeanId).beanClass(MultiTypeMapAntRequestMatcher.class)
				        .c().value(serviceApiPath + "/{type}/{res}").map()
				        .keyValueRefEntry("type", secureModelTypesBeanId)
				        .keyValueRefEntry("res", securedModelResourcesBeanId);

				builder.list(securedModelResourcesBeanId).values("create", "read", "update", "delete", "query",
				        "search", "link");
			} else {
				builder.comment("There are no models for this service.");
			}

			builder.comment("Security");
			builder.bean("securityRequestMatcher").beanClass(AntPathRequestMatcher.class).c().value("/**").nullArg()
			        .value("false");

			builder.bean("securityEntryPoint").beanClass(TokenAuthenticationEntryPoint.class);

			builder.bean("authenticationFilter").beanClass(LoginTokenAuthenticationFilter.class).c().bean()
			        .beanClass(LoginTokenAuthenticationFilterDelegateImpl.class).c()
			        .ref(this.getAppConfig().getAppBeans().getLoginTokenDecoderBeanId())
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

			SpringBeansXMLBeanBuilder<?> loginTokenUserDetailsBuilder = builder
			        .bean(this.getAppConfig().getAppBeans().getUtilityBeans().getLoginTokenUserDetailsBuilderBeanId());
			appSecurityBeansConfigurer.configureTokenUserDetailsBuilder(this.getAppConfig(),
			        loginTokenUserDetailsBuilder);

			builder.bean("loginTokenGrantedAuthorityBuilder").beanClass(LoginTokenGrantedAuthorityBuilderImpl.class).c()
			        .ref("loginGrantedAuthorityDecoder").array().bean().beanClass(SimpleGrantedAuthority.class).c()
			        .value("ROLE_USER").up().up().up().bean().beanClass(SimpleGrantedAuthority.class).c()
			        .value("ROLE_ANON");

			builder.bean("loginGrantedAuthorityDecoder").beanClass(GrantedAuthorityDecoderImpl.class)
			        .factoryMethod("withStringMap").c().map().keyType(Integer.class).valueType(String.class)
			        .value("0", "ROLE_ADMIN");

			builder.comment("Login Token System Factory");
			this.getAppConfig().getAppSecurityBeansConfigurer().configureSystemLoginTokenFactory(this.getAppConfig(),
			        builder);

			builder.comment("Secure Model Types");
			SpringBeansXMLListBuilder<?> secureModelsList = builder.list(secureModelTypesBeanId);

			for (LocalModelConfiguration modelConfig : secureLocalModelConfigs) {
				secureModelsList.ref(modelConfig.getModelTypeBeanId());
			}

			builder.comment("Exception Handler");
			builder.bean("loginTokenExceptionHandler").beanClass(ApiTokenExceptionHandler.class);
			builder.bean("accessDeniedHandler").beanClass(AccessDeniedHandlerImpl.class);

			builder.comment("Other Security");
			builder.comment("Query Overrides");
			builder.bean(this.getAppConfig().getAppBeans().getUtilityBeans().getAdminOnlySecurityModelQueryTaskBeanId())
			        .beanClass(AdminOnlySecurityModelQueryTask.class);
			builder.bean(this.getAppConfig().getAppBeans().getUtilityBeans().getAllowAllSecurityModelQueryTaskBeanId())
			        .beanClass(AllowAllSecurityModelQueryTask.class);

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
			http.intercept(serviceApiPath + "/login/auth/pass", RoleConfigImpl.make("permitAll"), HttpMethod.POST);
			http.intercept(serviceApiPath + "/login/auth/pass/**", RoleConfigImpl.make("permitAll"), HttpMethod.POST);
			http.intercept(serviceApiPath + "/login/auth/oauth/**", RoleConfigImpl.make("permitAll"), HttpMethod.POST);
			http.intercept(serviceApiPath + "/login/auth/key", RoleConfigImpl.make("permitAll"), HttpMethod.POST);

			http.getRawXMLBuilder().c("Token Matched");
			http.intercept(serviceApiPath + "/login/auth/system/token", RoleConfigImpl.make("permitAll"),
			        HttpMethod.POST);
			http.intercept(serviceApiPath + "/login/auth/token/**", RoleConfigImpl.make("permitAll"), HttpMethod.POST);

			http.intercept(serviceApiPath + "/**", RoleConfigImpl.make("denyAll"));

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
			        .value("system").up().up().property("methodMatcher").bean()
			        .beanClass(RequestMethodMatcherImpl.class).c().value("POST");

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
