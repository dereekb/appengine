package com.dereekb.gae.extras.gen.app.config.project.app.context;

import java.util.List;
import java.util.Properties;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppConfigurationUtility;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.project.app.AppBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.AppLoginTokenBeansConfiguration;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLListBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.server.app.model.app.info.impl.AppInfoImpl;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.security.app.service.impl.AppConfiguredAppLoginSecuritySigningServiceImpl;
import com.dereekb.gae.server.auth.security.app.service.impl.AppLoginSecurityDetailsServiceImpl;
import com.dereekb.gae.server.auth.security.app.service.impl.AppLoginSecurityServiceImpl;
import com.dereekb.gae.server.auth.security.app.service.impl.AppLoginSecuritySigningServiceImpl;
import com.dereekb.gae.server.auth.security.app.token.filter.LoginTokenAuthenticationFilterAppLoginSecurityVerifierImpl;
import com.dereekb.gae.server.auth.security.misc.AccessDeniedHandlerImpl;
import com.dereekb.gae.server.auth.security.model.roles.impl.CrudModelRole;
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
import com.dereekb.gae.server.auth.security.token.provider.details.impl.LoginTokenUserDetailsBuilderImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.LongModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringLongModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;
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
		builder.bean(appBeans.getAppKeyBeanId()).beanClass(ModelKey.class).property(appBeans.getAppIdBeanId());
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

			AppLoginTokenBeansConfiguration loginTokenBeansConfiguration = this.getAppConfig().getAppBeans()
			        .getAppLoginTokenBeansConfiguration();

			builder.comment("Login Server");
			builder.comment("Signatures");

			// TODO: Update to pull signatures from JSON file or similar file
			// not included in git.

			String loginTokenSignatureFactoryId = "loginTokenSignatureFactory";
			String refreshTokenSignatureFactoryId = "refreshTokenSignatureFactory";

			builder.bean(loginTokenSignatureFactoryId).beanClass(SignatureConfigurationFactory.class).getRawXMLBuilder()
			        .comment("TODO: Add production source.");
			builder.bean(refreshTokenSignatureFactoryId).beanClass(SignatureConfigurationFactory.class)
			        .getRawXMLBuilder().comment("TODO: Add production source.");

			builder.bean("loginTokenEncoderDecoder")
			        .beanClass(loginTokenBeansConfiguration.getLoginTokenEncoderDecoderClass()).c().bean()
			        .factoryBean(loginTokenSignatureFactoryId).factoryMethod("make");

			builder.bean("loginTokenBuilder").beanClass(loginTokenBeansConfiguration.getLoginTokenBuilderClass()).c().ref("loginRegistry").b


			builder.bean("loginTokenService").beanClass(LoginTokenServiceImpl.class).c()

			builder.comment("LoginToken Service");

			builder.comment("LoginPointer Service");

			builder.comment("Password Service");

			builder.comment("OAuth Service");

			builder.comment("KeyLogin Service");

			builder.comment("Register Service");

			builder.comment("Refresh Token Service");

		}

		public void addRemoteLoginServerLoginTokenBeansToXMLConfigurationFile(SpringBeansXMLBuilder builder) {

			AppLoginTokenBeansConfiguration loginTokenBeansConfiguration = this.getAppConfig().getAppBeans()
			        .getAppLoginTokenBeansConfiguration();

			builder.comment("Remote Login Service");

			// TODO: For Test, use local dencoder.
			// TODO: For Dev, send requests to the dev login service.
			// TODO: For Prod, send requests to the prod login service.

			builder.comment("Register Service");

		}

		public void addCommonLoginTokenBeansToXMLConfigurationFile(SpringBeansXMLBuilder builder) {

			builder.comment("Login Models Service");

			// TODO

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
				String roleString = StringUtility.firstLetterUpperCase(role.toString());
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

			builder.getRawXMLBuilder().elem("security:autentication-manager").a("alias", "loginAuthenticationManager")
			        .e("security:authentication-provider").a("ref", "loginTokenAuthenticationProvider");

			builder.comment("Authentication");
			builder.bean("loginTokenAuthenticationProvider")
			        .beanClass(this.getAppConfig().getAppBeans().getLoginTokenAuthenticationProviderClass());

			builder.bean("loginTokenUserDetailsBuilder").beanClass(LoginTokenUserDetailsBuilderImpl.class).c()
			        .ref("loginTokenModelContextSetDencoder").ref("loginTokenGrantedAuthorityBuilder")
			        .ref("loginRegistry").ref("loginPointerRegistry");

			builder.bean("loginTokenGrantedAuthorityBuilder").beanClass(LoginTokenGrantedAuthorityBuilderImpl.class).c()
			        .ref("loginGrantedAuthorityDecoder").array().bean().beanClass("SimpleGrantedAuthority").c()
			        .value("ROLE_USER").up().up().up().bean().beanClass("SimpleGrantedAuthority").c()
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

			String adminEncodedRole = "1";
			builder.bean(systemEncodedRolesId).beanClass(Login.class).c().value(adminEncodedRole);

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

			builder.bean("taskSchedulerAuthentication").beanClass(TaskSchedulerAuthenticatorImpl.class).c()
			        .ref(this.getAppConfig().getAppBeans().getSystemLoginTokenFactoryBeanId());

			return builder;
		}

	}

}
