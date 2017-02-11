package com.dereekb.gae.test.client;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.client.api.service.response.ClientApiResponseAccessor;
import com.dereekb.gae.client.api.service.response.builder.ClientApiResponseAccessorBuilder;
import com.dereekb.gae.client.api.service.response.builder.impl.ClientApiResponseAccessorBuilderImpl;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.error.ClientApiResponseErrorType;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.client.api.service.response.exception.NoClientResponseDataException;
import com.dereekb.gae.server.auth.security.token.exception.TokenException.TokenExceptionReason;
import com.dereekb.gae.utilities.web.error.ErrorInfo;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseDataImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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

	@Test
	public void testBuildFromErrorResponse() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		ClientApiResponseAccessorBuilder builder = new ClientApiResponseAccessorBuilderImpl(mapper);

		ApiResponseImpl response = new ApiResponseImpl();

		int missingTokenHttpResponseCode = HttpServletResponse.SC_UNAUTHORIZED;
		ApiResponseError error = TokenExceptionReason.MISSING_TOKEN.makeResponseError();
		response.addError(error);

		String responseJson = mapper.writeValueAsString(response);

		ClientApiResponseAccessor accessor = builder.buildAccessor(responseJson, missingTokenHttpResponseCode);

		Assert.assertTrue(accessor.getSuccess());
		Assert.assertNotNull(accessor.getIncludedData());

		try {
			accessor.getPrimaryData();
			Assert.fail();
		} catch (NoClientResponseDataException e) {

		}

		ClientResponseError clientResponseError = accessor.getError();
		ClientApiResponseErrorType clientErrorType = clientResponseError.getErrorType();

		Assert.assertTrue(clientErrorType == ClientApiResponseErrorType.AUTHENTICATION_ERROR);
		Assert.assertFalse(clientResponseError.getErrorInfo().isEmpty());

		List<ClientResponseErrorInfo> errorInfoList = clientResponseError.getErrorInfo();
		ErrorInfo errorInfo = errorInfoList.get(0);

		Assert.assertTrue(errorInfo.getErrorCode().equals(error.getErrorCode()));
	}

	@Test
	public void testBuildFromDataResponse() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		ClientApiResponseAccessorBuilder builder = new ClientApiResponseAccessorBuilderImpl(mapper);

		ApiResponseImpl response = new ApiResponseImpl();

		String listName = "list";

		List<String> dataStrings = new ArrayList<String>();
		dataStrings.add("A");
		dataStrings.add("B");
		dataStrings.add("C");

		ApiResponseData data = new ApiResponseDataImpl(listName, dataStrings);
		response.setData(data);

		String responseJson = mapper.writeValueAsString(response);

		ClientApiResponseAccessor accessor = builder.buildAccessor(responseJson);

		Assert.assertTrue(accessor.getSuccess());
		Assert.assertNotNull(accessor.getIncludedData());

		try {
			ClientApiResponseData responseData = accessor.getPrimaryData();

			Assert.assertTrue(responseData.getDataType().equalsIgnoreCase(listName));

			JsonNode jsonData = responseData.getDataJsonNode();
			String[] marshalledData = mapper.treeToValue(jsonData, String[].class);
			Assert.assertTrue(marshalledData.length == dataStrings.size());

		} catch (NoClientResponseDataException e) {
			Assert.fail();
		}

	}

}
