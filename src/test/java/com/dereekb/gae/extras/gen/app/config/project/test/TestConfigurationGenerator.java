package com.dereekb.gae.extras.gen.app.config.project.test;

import java.util.Properties;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.test.app.mock.context.AbstractAppTestingContext.TaskQueueCallbackHandler;
import com.dereekb.gae.test.server.auth.impl.TestPasswordLoginTokenContextImpl;
import com.dereekb.gae.test.server.auth.impl.TestRemoteLoginSystemLoginTokenContextImpl;
import com.dereekb.gae.test.server.auth.impl.TestSystemAuthenticationContextSetter;
import com.dereekb.gae.test.server.auth.security.login.password.impl.TestPasswordEncoderImpl;
import com.dereekb.gae.test.server.datastore.objectify.TestObjectifyInitializerImpl;
import com.dereekb.gae.test.spring.google.LocalDatastoreServiceTestConfigFactory;
import com.dereekb.gae.test.spring.web.builder.ServletAwareWebServiceRequestBuilder;
import com.dereekb.gae.utilities.misc.path.PathUtility;
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

		// Models
		folder.addFolder(new TestModelsConfigurationGenerator(this).generateConfigurations());

		folder.addFile(this.makeTestingFile(folder));
		folder.addFile(this.makeTestingWebFile(folder));

		return folder;
	}

	public GenFile makeTestingFile(GenFolderImpl folder) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Validator");
		builder.bean("validator").beanClass(LocalValidatorFactoryBean.class);

		builder.comment("GAE Specific");
		builder.bean("localServiceTestHelper").beanClass(LocalServiceTestHelper.class).lazy(false).c().array()
		        .ref("localMemcacheServiceTestConfig").ref("localBlobstoreServiceTestConfig")
		        .ref("localDatastoreServiceTestConfig").ref("localSearchServiceTestConfig")
		        .ref("localTaskQueueServiceTestConfig").ref("localImagesServiceTestConfig")
		        .ref("localURLFetchServiceTestConfig").ref("localAppIdentityServiceTestConfig");

		builder.bean("localMemcacheServiceTestConfig").beanClass(LocalMemcacheServiceTestConfig.class);

		builder.bean("localBlobstoreServiceTestConfig").beanClass(LocalBlobstoreServiceTestConfig.class);

		builder.bean("localDatastoreServiceTestConfig").factoryBean("localDatastoreServiceTestConfigFactory")
		        .factoryMethod("make");

		builder.bean("localDatastoreServiceTestConfigFactory").beanClass(LocalDatastoreServiceTestConfigFactory.class)
		        .property("noStorage").value("true").up().property("noIndexAutoGen").value("true");

		builder.bean("localSearchServiceTestConfig").beanClass(LocalSearchServiceTestConfig.class);

		builder.bean("localImagesServiceTestConfig").beanClass(LocalImagesServiceTestConfig.class);

		builder.bean("localURLFetchServiceTestConfig").beanClass(LocalURLFetchServiceTestConfig.class);

		builder.bean("localAppIdentityServiceTestConfig").beanClass(LocalAppIdentityServiceTestConfig.class);

		builder.stringBean("datastoreIndexesXmlPath", "src/main/webapp/WEB-INF/datastore-indexes.xml");

		builder.stringBean("taskQueueXmlPath", "src/main/webapp/WEB-INF/queue.xml");

		// Objectify Initializer is here as a helper. Required by CoreServiceTestingContext.
		builder.bean("test_" + this.getAppConfig().getAppBeans().getObjectifyInitializerId()).primary().lazy(false)
			.beanClass(TestObjectifyInitializerImpl.class);

		builder.bean("localTaskQueueServiceTestConfig").beanClass(LocalTaskQueueTestConfig.class)
		        .property("queueXmlPath").ref("taskQueueXmlPath").up().property("callbackClass")
		        .value(TaskQueueCallbackHandler.class.getCanonicalName()).up()
		        .property("disableAutoTaskExecution").value("false");

		return this.makeFileWithXML(TESTING_XML, builder);
	}

	public GenFile makeTestingWebFile(GenFolderImpl folder) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Simulates all requests coming to production API path.");

		builder.bean("defaultSecurityServletPath").beanClass(String.class).c().ref("productionApiServletPath");

		builder.stringBean("productionApiServletPath",
		        this.getAppConfig().getAppServiceConfigurationInfo().getRootAppApiPath());

		builder.stringBean("productionTaskqueueServletPath", "/taskqueue");

		builder.bean("testServiceRequestBuilder").beanClass(ServletAwareWebServiceRequestBuilder.class)
		        .property("defaultServletPath").ref("defaultSecurityServletPath").up().property("servletMappings")
		        .ref("testProductionServletMappings");

		builder.map("testProductionServletMappings").keyValueRefEntry("/taskqueue/**",
		        "productionTaskqueueServletPath");

		builder.comment("Import");

		// Import Components
		for (GenFolder subFolder : folder.getFolders()) {
			builder.importResource(
			        PathUtility.buildPath(subFolder.getFolderName(), subFolder.getFolderName() + ".xml"));
		}

		builder.comment("Override");

		SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> loginTokenContextBuilder = builder
		        .bean("testLoginTokenContext");

		if (this.getAppConfig().isLoginServer()) {
			loginTokenContextBuilder.beanClass(TestPasswordLoginTokenContextImpl.class).c()
			        .ref("passwordLoginController").ref("loginRegisterService").ref("loginTokenService")
			        .ref("loginPointerRegistry").ref("loginRegistry").up();

			// ???
			builder.bean("testSystemAuthenticationContextSetter").beanClass(TestSystemAuthenticationContextSetter.class).c()
			        .ref("loginTokenService").ref("systemLoginTokenFactory").ref("loginTokenAuthenticationProvider");
		} else {
			loginTokenContextBuilder.beanClass(TestRemoteLoginSystemLoginTokenContextImpl.class);
		}

		loginTokenContextBuilder.getRawXMLBuilder().comment("Admin Login");

		loginTokenContextBuilder.property("encodedAdminRoles").value("3");
		loginTokenContextBuilder.property("encodedRoles").value("0");

		builder.comment("Overrides the BCrypt Password Encoder For Testing");
		builder.bean("passwordEncoder").beanClass(TestPasswordEncoderImpl.class).primary().scope("singleton");

		return this.makeFileWithXML(TESTING_WEB_XML, builder);
	}

}
