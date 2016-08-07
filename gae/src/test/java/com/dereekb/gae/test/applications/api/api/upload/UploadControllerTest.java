package com.dereekb.gae.test.applications.api.api.upload;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.web.api.model.extension.upload.UploadApiExtensionController;
import com.dereekb.gae.web.api.model.extension.upload.exception.InvalidUploadTypeException;

/**
 * Tests the {@link UploadApiExtensionController}.
 *
 * @author dereekb
 *
 */
public class UploadControllerTest extends ApiApplicationTestContext {

	@Autowired
	private UploadApiExtensionController uploadController;

	// MARK: Mock
	@Test
	public void testMockInvalidUploadTypes() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/upload/new");
		requestBuilder.param("type", "INVALID_TYPE");

		MvcResult result = this.performHttpRequest(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		int status = response.getStatus();
		Assert.assertTrue(status == HttpServletResponse.SC_BAD_REQUEST);
	}

	// MARK: Non-Mock
	@Test
	public void testInvalidUploadTypes() {
		try {
			this.uploadController.makeFileUploadUrl("INVALID_TYPE");
			Assert.fail();
		} catch (InvalidUploadTypeException e) {

		}
	}

	@Test
	public void testValidUploadTypes() {
		try {
			this.uploadController.makeFileUploadUrl("icon");
		} catch (InvalidUploadTypeException e) {
			Assert.fail();
		}

	}

}
