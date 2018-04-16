package com.dereekb.gae.client.api.service.sender.impl;

import java.net.URI;
import java.util.Map.Entry;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestData;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.response.impl.ClientResponseImpl;
import com.dereekb.gae.client.api.service.sender.ClientRequestSender;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.misc.parameters.Parameters;

/**
 * {@link ClientRequestSender} implementation using Spring {@link RestTemplate}.
 *
 * @author dereekb
 *
 */
public class ClientRequestSenderImpl
        implements ClientRequestSender {

	private String baseUrl;

	public ClientRequestSenderImpl(String baseUrl) {
		this.setBaseUrl(baseUrl);
	}

	public String getBaseUrl() {
		return this.baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		if (StringUtility.isEmptyString(baseUrl)) {
			throw new IllegalArgumentException("baseUrl cannot be null.");
		}

		this.baseUrl = baseUrl;
	}

	// MARK: ClientRequestSender
	@Override
	public ClientResponse sendRequest(ClientRequest request)
	        throws ClientConnectionException,
	            ClientRequestFailureException {

		ClientRequestMethod method = request.getMethod();
		Parameters headers = request.getHeaders();
		Parameters parameters = request.getParameters();
		ClientRequestData data = request.getData();

		RestTemplate restTemplate = new RestTemplate();

		int statusCode;
		String responseData;

		try {
			ClientRequestUrl url = request.getUrl();
			String httpUrl = this.baseUrl + url.getRelativeUrlPath().getPath();

			HttpMethod httpMethod = methodForClientMethod(method);

			// Headers
			HttpHeaders httpHeaders = new HttpHeaders();

			for (Entry<String, String> entry : headers.getParameters().entrySet()) {
				httpHeaders.add(entry.getKey(), entry.getValue());
			}

			// Encode All URL Parameters
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(httpUrl);
			for (Entry<String, String> entry : parameters.getParameters().entrySet()) {
				builder.queryParam(entry.getKey(), entry.getValue());
			}

			URI httpUri = builder.build().encode().toUri();

			// Data
			HttpEntity<?> entity = null;

			if (data != null) {
				entity = new HttpEntity<String>(data.getDataString());
			}

			// Send Request
			ResponseEntity<String> response = restTemplate.exchange(httpUri, httpMethod, entity, String.class);

			// Wrap Response
			statusCode = response.getStatusCodeValue();
			responseData = response.getBody();

		} catch (HttpStatusCodeException e) {
			statusCode = e.getRawStatusCode();
			responseData = e.getResponseBodyAsString();
		} catch (ResourceAccessException e) {
			throw new ClientConnectionException(e);
		}

		return new ClientResponseImpl(statusCode, responseData);
	}

	// MARK: Utility
	public static HttpMethod methodForClientMethod(ClientRequestMethod method) {
		switch (method) {
			case DELETE:
				return HttpMethod.DELETE;
			case GET:
				return HttpMethod.GET;
			case POST:
				return HttpMethod.POST;
			case PUT:
				return HttpMethod.PUT;
			default:
				return HttpMethod.valueOf(method.toString());
		}
	}

	@Override
	public String toString() {
		return "ClientRequestSenderImpl [baseUrl=" + this.baseUrl + "]";
	}

}
