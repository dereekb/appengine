package com.dereekb.gae.extras.gen.app.config.project.test;

import java.util.Properties;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppSecurityBeansConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServicesConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.local.LoginTokenAppSecurityBeansConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.server.api.google.cloud.storage.impl.InMemoryGoogleCloudStorageService;
import com.dereekb.gae.server.auth.security.system.impl.SystemLoginTokenFactoryImpl;
import com.dereekb.gae.server.auth.security.system.impl.SystemLoginTokenServiceImpl;
import com.dereekb.gae.server.auth.security.token.gae.SignatureConfigurationFactory;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenServiceImpl;
import com.dereekb.gae.server.datastore.impl.NoopGetterImpl;
import com.dereekb.gae.server.initialize.impl.TestRemoteServerInitializeService;
import com.dereekb.gae.test.app.mock.context.AbstractAppTestingContext.TaskQueueCallbackHandler;
import com.dereekb.gae.test.app.mock.google.LocalDatastoreServiceTestConfigFactory;
import com.dereekb.gae.test.app.mock.web.builder.ServletAwareWebServiceRequestBuilder;
import com.dereekb.gae.test.server.auth.impl.TestPasswordLoginTokenContextImpl;
import com.dereekb.gae.test.server.auth.impl.TestSystemAuthenticationContextSetter;
import com.dereekb.gae.test.server.auth.security.login.password.impl.TestPasswordEncoderImpl;
import com.dereekb.gae.test.server.datastore.objectify.TestObjectifyInitializerImpl;
import com.google.appengine.tools.development.testing.LocalAppIdentityServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalBlobstoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalImagesServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.google.appengine.tools.development.testing.LocalURLFetchServiceTestConfig;

/**
 * Used for building test configuration that is used for local unit tests.
 *
 * @author dereekb
 *
 */
public class TestConfigurationGenerator extends AbstractConfigurationFileGenerator {

	private static final String TESTING_XML = "testing";
	private static final String TESTING_CONTEXT_XML = "testing-context";
	private static final String TESTING_WEB_XML = "testing-web";

	public static final String APP_FILE_NAME = "test";
	public static final String APP_FOLDER_NAME = "test";

	private String testFileName = APP_FILE_NAME;
	private String testFolderName = APP_FOLDER_NAME;

	public TestConfigurationGenerator(AppConfiguration testConfig, Properties outputProperties) {
		super(testConfig, outputProperties);
	}

	public TestConfigurationGenerator(AppConfiguration testConfig) {
		super(testConfig);
	}

	public String getTestFileName() {
		return this.testFileName;
	}

	public void setTestFileName(String testFileName) {
		if (testFileName == null) {
			throw new IllegalArgumentException("testFileName cannot be null.");
		}

		this.testFileName = testFileName;
	}

	public String getTestFolderName() {
		return this.testFolderName;
	}

	public void setTestFolderName(String testFolderName) {
		if (testFolderName == null) {
			throw new IllegalArgumentException("testFolderName cannot be null.");
		}

		this.testFolderName = testFolderName;
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public GenFolderImpl generateConfigurations() {
		GenFolderImpl folder = new GenFolderImpl(this.testFolderName);

		folder.addFile(this.makeTestingFile(folder));

		// Context
		GenFolderImpl context = new GenFolderImpl();
		context.addFolder(new TestContextModelsConfigurationGenerator(this).generateConfigurations());
		context.addFile(this.makeTestingContextFile(context));
		folder.merge(context);

		// API
		GenFolderImpl api = new GenFolderImpl();
		api.addFolder(new TestApiModelsConfigurationGenerator(this).generateConfigurations());
		api.addFile(this.makeTestingApiFile(api));
		folder.merge(api);

		return folder;
	}

	public GenFile makeTestingFile(GenFolderImpl folder) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Validator");
		builder.bean("validator").beanClass(LocalValidatorFactoryBean.class);

		builder.comment("GAE Specific");
		builder.bean("localServiceTestHelper").beanClass(LocalServiceTestHelper.class).lazy(false).c().array()
		        .ref("localMemcacheServiceTestConfig")	//.ref("localBlobstoreServiceTestConfig")		// No blobstore for now
		        .ref("localDatastoreServiceTestConfig").ref("localSearchServiceTestConfig")
		        .ref("localTaskQueueServiceTestConfig").ref("localImagesServiceTestConfig")
		        .ref("localURLFetchServiceTestConfig").ref("localAppIdentityServiceTestConfig");

		builder.bean("localMemcacheServiceTestConfig").beanClass(LocalMemcacheServiceTestConfig.class);

		builder.bean("localBlobstoreServiceTestConfig").beanClass(LocalBlobstoreServiceTestConfig.class)
		        .property("noStorage").value("true");

		builder.bean("localDatastoreServiceTestConfig").factoryBean("localDatastoreServiceTestConfigFactory")
		        .factoryMethod("make");

		builder.bean("localDatastoreServiceTestConfigFactory").beanClass(LocalDatastoreServiceTestConfigFactory.class)
		        .property("noStorage").value("true").up().property("noIndexAutoGen").value("true");

		builder.bean("localSearchServiceTestConfig").beanClass(LocalSearchServiceTestConfig.class);

		builder.bean("localImagesServiceTestConfig").beanClass(LocalImagesServiceTestConfig.class);

		builder.bean("localURLFetchServiceTestConfig").beanClass(LocalURLFetchServiceTestConfig.class);

		builder.bean("localAppIdentityServiceTestConfig").beanClass(LocalAppIdentityServiceTestConfig.class);

		// Development queue.xml - devqueue.xml
		builder.stringBean("taskQueueXmlPath", "src/main/webapp/WEB-INF/devqueue.xml");

		// Objectify Initializer is here as a helper. Required by
		// CoreServiceTestingContext.
		builder.bean("test_" + this.getAppConfig().getAppBeans().getObjectifyInitializerId()).primary().lazy(false)
		        .beanClass(TestObjectifyInitializerImpl.class);

		builder.bean("localTaskQueueServiceTestConfig").beanClass(LocalTaskQueueTestConfig.class)
		        .property("queueXmlPath").ref("taskQueueXmlPath").up().property("callbackClass")
		        .value(TaskQueueCallbackHandler.class.getCanonicalName()).up().property("disableAutoTaskExecution")
		        .value("false");

		return this.makeFileWithXML(TESTING_XML, builder);
	}

	public GenFile makeTestingContextFile(GenFolderImpl folder) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		// Import Components
		builder.comment("Import");
		this.importFilesWithBuilder(builder, folder, true, true);

		// Server Initializer
		// TODO: Allow configuring whether or not this is generated. Also only
		// build if App is available locally.
		if (this.getAppConfig().isRootServer() == false) {

			/*
			 * Non-root servers need to initialize their own App, otherwise
			 * components attempting to load the app will fail.
			 */

			String testServerInitializerBeanId = "testServerInitializer";

			builder.bean(testServerInitializerBeanId).beanClass(TestRemoteServerInitializeService.class).c()
			        .ref(this.getAppConfig().getAppBeans().getAppInfoBeanId()).ref("appRegistry").ref("appRegistry");
		}

		// Service Override Configurations
		builder.comment("Testing Overrides");
		AppServicesConfigurer servicesConfigurer = this.getAppConfig().getAppServicesConfigurer();

		if (servicesConfigurer.getAppGoogleCloudStorageServiceConfigurer() != null) {
			builder.bean(this.getAppConfig().getAppBeans().getGoogleCloudStorageServiceBeanId())
			        .beanClass(InMemoryGoogleCloudStorageService.class).primary().getRawXMLBuilder()
			        .comment("Google Cloud Service override for unit tests");
		}

		return this.makeFileWithXML(TESTING_CONTEXT_XML, builder);
	}

	public GenFile makeTestingApiFile(GenFolderImpl folder) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Simulates all requests coming to production API path.");

		builder.bean("defaultSecurityServletPath").beanClass(String.class).c().ref("productionApiServletPath");

		builder.stringBean("productionApiServletPath",
		        this.getAppConfig().getAppServiceConfigurationInfo().getFullDomainRootAppApiPath());

		builder.stringBean("productionTaskqueueServletPath", "/taskqueue");

		builder.bean("testServiceRequestBuilder").beanClass(ServletAwareWebServiceRequestBuilder.class)
		        .property("defaultServletPath").ref("defaultSecurityServletPath").up().property("servletMappings")
		        .ref("testProductionServletMappings");

		builder.map("testProductionServletMappings").keyValueRefEntry("/taskqueue/**",
		        "productionTaskqueueServletPath");

		// Import Components
		builder.comment("Import");
		this.importFilesWithBuilder(builder, folder, true, true);

		builder.comment("Override");

		String testLoginTokenContextBeanId = "testLoginTokenContext";

		SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> loginTokenContextBuilder = builder
		        .bean(testLoginTokenContextBeanId);

		if (this.getAppConfig().isLoginServer()) {
			loginTokenContextBuilder.beanClass(TestPasswordLoginTokenContextImpl.class).c()
			        .ref("passwordLoginController").ref("loginRegisterService").ref("loginTokenService")
			        .ref("loginPointerRegistry").ref("loginRegistry").up();

			// ???
			builder.bean("testSystemAuthenticationContextSetter").beanClass(TestSystemAuthenticationContextSetter.class)
			        .c().ref("loginTokenService").ref("systemLoginTokenFactory")
			        .ref("loginTokenAuthenticationProvider");
		} else {

			// Remote Logins use configuration similar to that described in
			// LocalAppLoginTokenSecurityConfigurerImpl

			AppConfiguration appConfig = this.getAppConfig();

			AppSecurityBeansConfigurer appSecurityBeansConfigurer = appConfig.getAppSecurityBeansConfigurer();

			String testLoginTokenSignatureFactoryId = "testLoginTokenSignatureFactory";
			builder.bean(testLoginTokenSignatureFactoryId).beanClass(SignatureConfigurationFactory.class);

			builder.comment("Test LoginToken Service");
			String testLoginTokenEncoderDecoderBeanId = "testLoginTokenEncoderDecoder";
			SpringBeansXMLBeanBuilder<?> loginTokenEncoderDecoderBuilder = builder
			        .bean(testLoginTokenEncoderDecoderBeanId);
			appSecurityBeansConfigurer.configureTokenEncoderDecoder(appConfig, loginTokenEncoderDecoderBuilder, true);

			String testLoginGetterBeanId = LoginTokenAppSecurityBeansConfigurerImpl.TEST_LOGIN_TOKEN_BUILDER_LOGIN_GETTER_BEAN_ID;
			builder.bean(testLoginGetterBeanId).beanClass(NoopGetterImpl.class);

			String testLoginTokenBuilderBeanId = "testLoginTokenBuilder";
			SpringBeansXMLBeanBuilder<?> loginTokenBuilderBuilder = builder.bean(testLoginTokenBuilderBeanId);
			appSecurityBeansConfigurer.configureTokenBuilder(appConfig, loginTokenBuilderBuilder, true);

			String testLoginTokenServiceId = "testLoginTokenService";
			builder.bean(testLoginTokenServiceId).beanClass(LoginTokenServiceImpl.class).c()
			        .ref(testLoginTokenBuilderBeanId).ref(testLoginTokenEncoderDecoderBeanId);

			appSecurityBeansConfigurer.configureTestRemoteLoginSystemLoginTokenContext(appConfig,
			        loginTokenContextBuilder);

			// Also Override System Login Token Factory
			// TODO: Move elsewhere?
			String systemLoginTokenServiceBeanId = appConfig.getAppBeans().getSystemLoginTokenServiceBeanId();

			builder.bean(systemLoginTokenServiceBeanId).beanClass(SystemLoginTokenServiceImpl.class).c().value("1")	// Default
			                                                                                                       	// System
			                                                                                                       	// Encoded
			                                                                                                       	// Roles
			        .ref(testLoginTokenServiceId);

			builder.bean(appConfig.getAppBeans().getSystemLoginTokenFactoryBeanId())
			        .beanClass(SystemLoginTokenFactoryImpl.class).c().ref(systemLoginTokenServiceBeanId)
			        .ref(appConfig.getAppBeans().getAppLoginSecuritySigningServiceBeanId());

		}

		loginTokenContextBuilder.getRawXMLBuilder().comment("Admin Login");

		loginTokenContextBuilder.property("encodedAdminRoles").value("3");
		loginTokenContextBuilder.property("encodedRoles").value("0");

		builder.comment("Overrides the BCrypt Password Encoder For Testing");
		builder.bean("passwordEncoder").beanClass(TestPasswordEncoderImpl.class).primary().scope("singleton");

		return this.makeFileWithXML(TESTING_WEB_XML, builder);
	}

}
