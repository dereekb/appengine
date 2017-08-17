package com.dereekb.gae.test.spring;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;

/**
 * {@link CoreServiceTestingContext} extension that includes the API application
 * configurations.
 * 
 * @author dereekb
 *
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ @ContextConfiguration(name = "api", locations = {
        ApiApplicationTestContext.API_APPLICATION_XML_PATH, WebServiceTestingContextImpl.WEB_TESTING_XML_PATH }) })
public class CoreApiServiceTestingContext extends CoreServiceTestingContext {}
