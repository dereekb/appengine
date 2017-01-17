package com.dereekb.gae.test.applications.core;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.spring.CoreServiceTestingContext;

/**
 * Initializes the application but skips all web configuration.
 * 
 * @author dereekb
 * @see {@link ApiApplicationTestContext} for web-configuration and
 *      authentication components.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(name = "api", locations = { ApiApplicationTestContext.API_APPLICATION_XML_PATH }) })
public class CoreApplicationTestContext extends CoreServiceTestingContext {

	public static final String API_APPLICATION_XML_PATH = CoreServiceTestingContext.APPLICATION_TESTING_PATH
	        + "api/spring.xml";

}