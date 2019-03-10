package com.dereekb.gae.test.spring.context;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * Testing context that only loads the "context" portion of the application before executing tests.
 *
 * @author dereekb
 *
 */
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig( name = "testing", locations = { AbstractGaeTestingContext.GAE_TESTING_XML_PATH })
public abstract class AbstractGaeTestingContext {

	public static final String SRC_PATH = "file:src/";
	public static final String BASE_TESTING_PATH = SRC_PATH + "test/webapp/spring/";
	public static final String GAE_TESTING_XML_PATH = BASE_TESTING_PATH + "testing.xml";

	public static final String APPLICATION_TESTING_PATH = BASE_TESTING_PATH + "applications/";

	public static final String BASE_MAIN_PATH = SRC_PATH + "main/webapp/WEB-INF/";

	@Autowired
	protected ApplicationContext applicationContext;

	@Autowired
	protected LocalServiceTestHelper helper;

	@BeforeEach
	public void setUpAppServices() {
		this.initializeLocalServices();
	}

	@AfterEach
	public void tearDownAppServices() {
		this.resetLocalServices();
	}

	// MARK: Initialize
	protected final void initializeLocalServices() {
		this.helper.setUp();
	}

	protected final void resetLocalServices() {
		this.helper.tearDown();
	}


}
