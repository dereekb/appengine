package com.dereekb.gae.test.applications.api.api.extension;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.applications.api.api.tests.client.ClientApiLinkTest;
import com.dereekb.gae.web.api.model.extension.link.LinkExtensionApiController;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeImpl;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeRequest;
import com.google.gson.Gson;

/**
 * Used for testing the {@link LinkExceptionApiController}.
 * 
 * @author dereekb
 *
 * @deprecated Use a {@link ClientApiLinkTest} instead.
 */
@Ignore
@Deprecated
public class LinkExtensionApiControllerTest extends ApiApplicationTestContext {

	protected static final Gson gson = new Gson();

	@Autowired
	private LinkExtensionApiController controller;

	public LinkExtensionApiController getController() {
		return this.controller;
	}

	public void setController(LinkExtensionApiController controller) {
		this.controller = controller;
	}

	@Test
	public void testControllerBean() {
		Assert.assertNotNull("Controller was not null.", this.controller);
		Assert.assertNotNull("Controller's Converter was not null.", this.controller.getConverter());
		Assert.assertNotNull("Controller's Service was not null.", this.controller.getService());
	}

	/**
	 * Tests linking to an element that does not exist..?
	 */
	@Test
	@Deprecated
	public void testLinkingFailure() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = this.serviceRequestBuilder.put("/geoplace/link");
		requestBuilder.accept("application/json");
		requestBuilder.contentType("application/json");

		ApiLinkChangeRequest request = new ApiLinkChangeRequest();
		ApiLinkChangeImpl change = new ApiLinkChangeImpl();
		change.setAction("link");
		change.setPrimaryKey("123");
		change.setLinkName("parent");

		Set<String> targetKeys = new HashSet<>();
		targetKeys.add("123");

		change.setTargetKeys(targetKeys);

		request.setDataElement(change);

		String requestJson = gson.toJson(request);
		requestBuilder.content(requestJson);

		MvcResult result = this.performHttpRequest(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		// Atomic operation exception.
		int status = response.getStatus();
		Assert.assertTrue(status == HttpServletResponse.SC_GONE);

		String content = response.getContentAsString();
		Assert.assertFalse(content.isEmpty());
	}

}
