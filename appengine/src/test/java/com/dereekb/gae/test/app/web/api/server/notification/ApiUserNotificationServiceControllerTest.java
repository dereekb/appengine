package com.dereekb.gae.test.app.web.api.server.notification;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.dereekb.gae.server.notification.model.token.dto.NotificationTokenData;
import com.dereekb.gae.test.app.mock.context.AbstractAppTestingContext;
import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.dereekb.gae.web.api.server.notification.ApiUserNotificationServiceController;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Test for the {@link ApiUserNotificationServiceController}.
 *
 * @author dereekb
 *
 */
public class ApiUserNotificationServiceControllerTest extends AbstractAppTestingContext {

	@Test
	public void testUpdatingDevice() throws Exception {

		NotificationTokenData device = new NotificationTokenData("UUID", "TEST", new Date());
		MockHttpServletRequestBuilder request = this.buildRequestForDevice(device);

		MvcResult result = this.performHttpRequest(request).andReturn();
		MockHttpServletResponse response = result.getResponse();

		int status = response.getStatus();
		assertTrue(status == HttpServletResponse.SC_OK, "Expected 200, but got " + status);
	}

	@Test
	public void testUpdatingInvalidDeviceFails() throws Exception {

		// Set null/invalid values.
		NotificationTokenData device = new NotificationTokenData(null, "", new Date());
		MockHttpServletRequestBuilder request = this.buildRequestForDevice(device);

		MvcResult result = this.performHttpRequest(request).andReturn();
		MockHttpServletResponse response = result.getResponse();

		int status = response.getStatus();
		assertTrue(status == HttpServletResponse.SC_BAD_REQUEST, "Expected 400, but got " + status);
	}

	private MockHttpServletRequestBuilder buildRequestForDevice(NotificationTokenData device)
	        throws JsonProcessingException {

		MockHttpServletRequestBuilder requestBuilder = this.serviceRequestBuilder.put("/notification/device/add");
		requestBuilder.accept("application/json");
		requestBuilder.contentType("application/json");

		String requestJson = ObjectMapperUtilityBuilderImpl.MAPPER.writeValueAsString(device);
		requestBuilder.content(requestJson);

		return requestBuilder;
	}

}
