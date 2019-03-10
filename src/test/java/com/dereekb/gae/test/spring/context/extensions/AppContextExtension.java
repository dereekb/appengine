package com.dereekb.gae.test.spring.context.extensions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * JUnit5 Extension for loading the testing context.
 *
 * @author dereekb
 *
 */
@ExtendWith(SpringExtension.class)
@ContextHierarchy({
    @ContextConfiguration(name = "testing", locations = { AppContextExtension.GAE_TESTING_XML_PATH }) })
public class AppContextExtension implements BeforeEachCallback, AfterEachCallback {

	public static final String SRC_PATH = "file:src/";
	public static final String BASE_TESTING_PATH = SRC_PATH + "test/webapp/spring/";
	public static final String GAE_TESTING_XML_PATH = BASE_TESTING_PATH + "testing.xml";

	public static final String APPLICATION_TESTING_PATH = BASE_TESTING_PATH + "applications/";

	public static final String BASE_MAIN_PATH = SRC_PATH + "main/webapp/WEB-INF/";

	@Autowired
	protected ApplicationContext applicationContext;


	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		System.out.println("TEST");
	}

	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		System.out.println("TEST");
	}


}
