package com.dereekb.gae.test.spring;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dereekb.gae.test.server.auth.impl.TestAuthenticationContext;
import com.dereekb.gae.test.server.datastore.objectify.TestObjectifyInitializerImpl;
import com.dereekb.gae.web.api.server.initialize.ApiInitializeServerController;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * Testing context that initializes a Google App Engine
 * {@link LocalServiceTestHelper} from a Spring IoC container.
 *
 * @author dereekb
 *
 * @see CoreApiServiceTestingContext
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(name = "testing", locations = { CoreServiceTestingContext.GAE_TESTING_XML_PATH }) })
public class CoreServiceTestingContext {

	public static final String SRC_PATH = "file:src/";
	public static final String BASE_TESTING_PATH = SRC_PATH + "test/webapp/spring/";
	public static final String GAE_TESTING_XML_PATH = BASE_TESTING_PATH + "testing.xml";

	public static final String APPLICATION_TESTING_PATH = BASE_TESTING_PATH + "applications/";

	public static final String BASE_MAIN_PATH = SRC_PATH + "main/webapp/WEB-INF/";

	protected boolean initializeServerWithCoreServices = true;

	@Autowired
	protected ApplicationContext applicationContext;

	@Autowired
	protected LocalServiceTestHelper helper;

	@Autowired(required = true)
	protected TestObjectifyInitializerImpl testObjectifyInitializer;

	@Autowired(required = false)
	protected TestAuthenticationContext authContext;

	@Autowired(required = false)
	protected ApiInitializeServerController initializeServerController;

	@Before
	public void setUpCoreServices() {
		this.testObjectifyInitializer.begin();
		this.helper.setUp();

		if (this.initializeServerWithCoreServices) {
			this.initializeServerAndAuthContext();
		}
	}

	public void initializeServerAndAuthContext() {
		this.initializeServer();
		this.resetAuthContext();
	}

	public final void resetAuthContext() {
		if (this.authContext != null) {
			this.authContext.resetContext();
		}
	}

	protected void initializeServer() {
		if (this.initializeServerController != null) {
			this.initializeServerController.initialize();
		}
	}

	@After
	public void tearDownCoreServices() {
		this.testObjectifyInitializer.reset();
		this.helper.tearDown();
	}

	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public LocalServiceTestHelper getHelper() {
		return this.helper;
	}

	public void setHelper(LocalServiceTestHelper helper) {
		this.helper = helper;
	}

}
