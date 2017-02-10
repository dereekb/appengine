package com.dereekb.gae.test.client;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.client.api.service.response.ClientApiResponseAccessor;
import com.dereekb.gae.client.api.service.response.builder.ClientApiResponseAccessorBuilder;
import com.dereekb.gae.client.api.service.response.builder.impl.ClientApiResponseAccessorBuilderImpl;
import com.dereekb.gae.client.api.service.response.exception.NoClientResponseDataException;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests Client API Response components.
 * 
 * @author dereekb
 *
 */
public class ClientApiResponseTest {

	@Test
	public void testBuildFromCleanResponse() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		ClientApiResponseAccessorBuilder builder = new ClientApiResponseAccessorBuilderImpl(mapper);

		ApiResponseImpl response = new ApiResponseImpl();

		String responseJson = mapper.writeValueAsString(response);

		ClientApiResponseAccessor accessor = builder.buildAccessor(responseJson);

		Assert.assertTrue(accessor.getSuccess());

		try {
			accessor.getPrimaryData();
			Assert.fail();
		} catch (NoClientResponseDataException e) {

		}

		Assert.assertNotNull(accessor.getIncludedData());
	}

	// TODO

}
