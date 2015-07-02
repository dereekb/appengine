package com.dereekb.gae.test.spring;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;

/**
 * Testing context that initializes a Google App Engine {@link LocalServiceTestHelper} from a Spring IoC container.
 *
 * @author dereekb
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ @ContextConfiguration(name = "testing", locations = { CoreServiceTestingContext.GAE_TESTING_XML_PATH }) })
public class CoreServiceTestingContext {

	public static final String BASE_TESTING_PATH = "file:src/test/webapp/spring/";
	public static final String APPLICATION_TESTING_PATH = BASE_TESTING_PATH + "applications/";
	public static final String GAE_TESTING_XML_PATH = BASE_TESTING_PATH + "testing.xml";

	private Closeable session;

	@Autowired
	protected ApplicationContext applicationContext;

	@Autowired
	protected LocalServiceTestHelper helper;

	@Before
	public void setUp() {
		this.helper.setUp();
		this.session = ObjectifyService.begin();
	}

	@After
	public void tearDown() {
		this.helper.tearDown();
		this.session.close();
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
