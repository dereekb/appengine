package com.dereekb.gae.test.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;

import com.dereekb.gae.client.api.service.response.ClientApiResponseAccessor;
import com.dereekb.gae.client.api.service.response.builder.impl.ClientApiResponseAccessorBuilderImpl;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.error.ClientApiResponseErrorType;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.client.api.service.response.exception.NoClientResponseDataException;
import com.dereekb.gae.client.api.service.response.impl.ClientResponseImpl;
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
		ClientApiResponseAccessorBuilderImpl builder = new ClientApiResponseAccessorBuilderImpl(mapper);

		ApiResponseImpl response = new ApiResponseImpl();

		String responseJson = mapper.writeValueAsString(response);

		ClientResponseImpl clientResponse = new ClientResponseImpl(200, responseJson);
		ClientApiResponseAccessor accessor = builder.buildAccessor(clientResponse);

		assertTrue(accessor.isSuccessful());

		try {
			accessor.getPrimaryData();
			fail();
		} catch (NoClientResponseDataException e) {

		}

		assertNotNull(accessor.getIncludedData());
	}

	@Test
	public void testBuildFromErrorResponse() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		ClientApiResponseAccessorBuilderImpl builder = new ClientApiResponseAccessorBuilderImpl(mapper);

		ApiResponseImpl response = new ApiResponseImpl();

		int missingTokenHttpResponseCode = HttpServletResponse.SC_UNAUTHORIZED;
		ApiResponseError error = TokenExceptionReason.MISSING_TOKEN.makeResponseError();
		response.addError(error);

		String responseJson = mapper.writeValueAsString(response);
		ClientResponseImpl clientResponse = new ClientResponseImpl(missingTokenHttpResponseCode, responseJson);
		ClientApiResponseAccessor accessor = builder.buildAccessor(clientResponse);

		assertTrue(accessor.isSuccessful());
		assertNotNull(accessor.getIncludedData());

		try {
			accessor.getPrimaryData();
			fail();
		} catch (NoClientResponseDataException e) {

		}

		ClientResponseError clientResponseError = accessor.getError();
		ClientApiResponseErrorType clientErrorType = clientResponseError.getErrorType();

		assertTrue(clientErrorType == ClientApiResponseErrorType.AUTHENTICATION_ERROR);
		assertFalse(clientResponseError.getErrorInfo().isEmpty());

		List<ClientResponseErrorInfo> errorInfoList = clientResponseError.getErrorInfo();
		ErrorInfo errorInfo = errorInfoList.get(0);

		assertTrue(errorInfo.getErrorCode().equals(error.getErrorCode()));
	}

	@Test
	public void testBuildFromDataResponse() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		ClientApiResponseAccessorBuilderImpl builder = new ClientApiResponseAccessorBuilderImpl(mapper);

		ApiResponseImpl response = new ApiResponseImpl();

		String listName = "list";

		List<String> dataStrings = new ArrayList<String>();
		dataStrings.add("A");
		dataStrings.add("B");
		dataStrings.add("C");

		ApiResponseData data = new ApiResponseDataImpl(listName, dataStrings);
		response.setData(data);

		String responseJson = mapper.writeValueAsString(response);

		ClientResponseImpl clientResponse = new ClientResponseImpl(200, responseJson);
		ClientApiResponseAccessor accessor = builder.buildAccessor(clientResponse);

		assertTrue(accessor.isSuccessful());
		assertNotNull(accessor.getIncludedData());

		try {
			ClientApiResponseData responseData = accessor.getPrimaryData();

			assertTrue(responseData.getDataType().equalsIgnoreCase(listName));

			JsonNode jsonData = responseData.getJsonNode();
			String[] marshalledData = mapper.treeToValue(jsonData, String[].class);
			assertTrue(marshalledData.length == dataStrings.size());

		} catch (NoClientResponseDataException e) {
			fail();
		}

	}

	@Test
	public void testBuildIncludedInDataResponse() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		ClientApiResponseAccessorBuilderImpl builder = new ClientApiResponseAccessorBuilderImpl(mapper);

		ApiResponseImpl response = new ApiResponseImpl();

		String listName = "list";

		List<String> dataStrings = new ArrayList<String>();
		dataStrings.add("A");
		dataStrings.add("B");
		dataStrings.add("C");

		ApiResponseData data = new ApiResponseDataImpl(listName, dataStrings);
		response.setData(data);

		String includedDataAName = "a";
		ApiResponseData includedDataA = new ApiResponseDataImpl(includedDataAName, dataStrings);
		response.addIncluded(includedDataA);

		String includedDataBName = "b";
		ApiResponseData includedDataB = new ApiResponseDataImpl(includedDataBName, dataStrings);
		response.addIncluded(includedDataB);

		String responseJson = mapper.writeValueAsString(response);

		ClientResponseImpl clientResponse = new ClientResponseImpl(200, responseJson);
		ClientApiResponseAccessor accessor = builder.buildAccessor(clientResponse);

		// Read Included Data
		Map<String, ClientApiResponseData> responseData = accessor.getIncludedData();

		ClientApiResponseData includedAData = responseData.get(includedDataAName);
		assertTrue(includedAData.getDataType().equals(includedDataAName));

		ClientApiResponseData includedBData = responseData.get(includedDataBName);
		assertTrue(includedBData.getDataType().equals(includedDataBName));

	}

}
