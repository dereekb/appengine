package com.dereekb.gae.test.applications.api;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dereekb.gae.test.app.mock.client.crud.MockClientRequestSender;
import com.dereekb.gae.test.applications.core.CoreApplicationTestContext;
import com.dereekb.gae.test.spring.CoreServiceTestingContext;
import com.dereekb.gae.test.spring.WebServiceTestingContextImpl;

/**
 * Initializes the web app.
 *
 * @author dereekb
 *
 * @see CoreApplicationTestContext for core-application only.
 */
@Deprecated
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ @ContextConfiguration(name = "api", locations = {
        ApiApplicationTestContext.API_APPLICATION_XML_PATH, WebServiceTestingContextImpl.WEB_TESTING_XML_PATH }) })
public class ApiApplicationTestContext extends WebServiceTestingContextImpl {

	public static final String API_APPLICATION_XML_PATH = CoreServiceTestingContext.BASE_MAIN_PATH + "spring/app.xml";

	@Autowired
	@Qualifier("mockClientRequestSender")
	private MockClientRequestSender mockRequestSender;

	@Before
	public void initializeMockRequestSender() {
		this.mockRequestSender.setWebServiceTester(this);
	}

}
