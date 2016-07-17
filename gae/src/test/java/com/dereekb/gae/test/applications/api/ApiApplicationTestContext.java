package com.dereekb.gae.test.applications.api;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dereekb.gae.test.spring.CoreServiceTestingContext;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ @ContextConfiguration(name = "api", locations = { ApiApplicationTestContext.API_APPLICATION_XML_PATH }) })
public class ApiApplicationTestContext extends CoreServiceTestingContext {

	public static final String API_APPLICATION_XML_PATH = CoreServiceTestingContext.APPLICATION_TESTING_PATH
	        + "api/spring.xml";

	@Autowired
	protected WebApplicationContext webApplicationContext;

	protected MockMvc mockMvc;

	@Before
	public void setUpApi() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).apply(springSecurity()).build();
		TestLocalTaskQueueCallback.mockMvc = this.mockMvc;
	}

	public WebApplicationContext getWebApplicationContext() {
		return this.webApplicationContext;
	}

	public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
		this.webApplicationContext = webApplicationContext;
	}

	public MockMvc getMockMvc() {
		return this.mockMvc;
	}

	public void setMockMvc(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

}
