package com.dereekb.gae.test.applications.api.api.upload;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.dereekb.gae.test.deprecated.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.web.api.model.extension.upload.UploadApiExtensionController;
import com.dereekb.gae.web.api.model.extension.upload.exception.InvalidUploadTypeException;

/**
 * Tests the {@link UploadApiExtensionController}.
 *
 * @author dereekb
 *
 */
@Disabled
public class UploadControllerTest extends ApiApplicationTestContext {

	@Autowired
	private UploadApiExtensionController uploadController;

	// MARK: Mock
	@Test
	public void testMockInvalidUploadTypes() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = this.serviceRequestBuilder.post("/upload/new");
		requestBuilder.param("type", "INVALID_TYPE");

		MvcResult result = this.performHttpRequest(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		int status = response.getStatus();
		assertTrue(status == HttpServletResponse.SC_BAD_REQUEST);
	}

	// MARK: Non-Mock
	@Test
	public void testInvalidUploadTypes() {
		try {
			this.uploadController.makeFileUploadUrl("INVALID_TYPE");
			fail();
		} catch (InvalidUploadTypeException e) {

		}
	}

	@Test
	public void testValidUploadTypes() {
		try {
			this.uploadController.makeFileUploadUrl("icon");
		} catch (InvalidUploadTypeException e) {
			fail();
		}

	}

}
