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
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;

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

	private Closeable session;

	@Autowired
	protected ApplicationContext applicationContext;

	@Autowired
	protected LocalServiceTestHelper helper;

	@Autowired(required=false)
	protected TestAuthenticationContext authContext;

	@Before
	public void setUpCoreServices() {
		if (this.session == null) {
			this.helper.setUp();
			this.session = ObjectifyService.begin();
		}
	}

	@Before
	public final void resetAuthContext() {
		if (this.authContext != null) {
			this.authContext.resetContext();
		}
	}

	@After
	public void tearDownCoreServices() {
		if (this.session != null) {
			this.session.close();
			this.helper.tearDown();
		}
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
