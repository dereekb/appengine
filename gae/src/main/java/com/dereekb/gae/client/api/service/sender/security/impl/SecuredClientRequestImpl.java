package com.dereekb.gae.client.api.service.sender.security.impl;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestData;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientRequest;
import com.dereekb.gae.server.auth.security.token.parameter.AuthenticationParameterBuilder;
import com.dereekb.gae.server.auth.security.token.parameter.impl.AuthenticationParameterServiceImpl;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.Parameters;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;

/**
 * {@link SecuredClientRequest} implementation that wraps a
 * {@link ClientRequest}.
 * 
 * @author dereekb
 *
 */
public class SecuredClientRequestImpl
        implements SecuredClientRequest {

	private String token;
	private ClientRequest request;

	private AuthenticationParameterBuilder authParameterBuilder = AuthenticationParameterServiceImpl.SINGLETON;

	public SecuredClientRequestImpl(String token, ClientRequest request) {
		this.setToken(token);
		this.setRequest(request);
	}

	public SecuredClientRequestImpl(String token,
	        ClientRequest request,
	        AuthenticationParameterBuilder authParameterBuilder) {
		this.setToken(token);
		this.setRequest(request);
		this.setAuthParameterBuilder(authParameterBuilder);
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) throws IllegalArgumentException {
		if (token == null || token.isEmpty()) {
			throw new IllegalArgumentException("Security token cannot be null or empty");
		}

		this.token = token;
	}

	public ClientRequest getRequest() {
		return this.request;
	}

	public void setRequest(ClientRequest request) throws IllegalArgumentException {
		if (request == null) {
			throw new IllegalArgumentException("Request cannot be null.");
		}

		this.request = request;
	}

	public AuthenticationParameterBuilder getAuthParameterBuilder() {
		return this.authParameterBuilder;
	}

	public void setAuthParameterBuilder(AuthenticationParameterBuilder authParameterBuilder) {
		if (authParameterBuilder == null) {
			throw new IllegalArgumentException("authParameterBuilder cannot be null.");
		}

		this.authParameterBuilder = authParameterBuilder;
	}

	// MARK: ClientRequest
	@Override
	public ClientRequestUrl getUrl() {
		return this.request.getUrl();
	}

	@Override
	public ClientRequestMethod getMethod() {
		return this.request.getMethod();
	}

	@Override
	public Parameters getHeaders() {
		return new SecuredHeaders(this.token, this.request.getHeaders());
	}

	@Override
	public Parameters getParameters() {
		return this.request.getParameters();
	}

	@Override
	public ClientRequestData getData() {
		return this.request.getData();
	}

	// MARK: SecuredClientRequest
	@Override
	public String getEncodedLoginToken() {
		return this.getToken();
	}

	// MARK: Internal
	public KeyedEncodedParameter getAuthenticationParameter() {
		return this.authParameterBuilder.buildAuthenticationParameter(this.token);
	}

	private class SecuredHeaders
	        implements Parameters {

		private final String token;
		private final Parameters parameters;

		public SecuredHeaders(String token, Parameters parameters) {
			this.token = token;
			this.parameters = parameters;
		}

		@Override
		public Map<String, String> getParameters() {
			Map<String, String> parameters = new HashMap<String, String>();

			if (this.parameters != null) {
				parameters.putAll(this.parameters.getParameters());
			}

			KeyedEncodedParameter authParameter = SecuredClientRequestImpl.this.authParameterBuilder
			        .buildAuthenticationParameter(this.token);
			ParameterUtility.put(parameters, authParameter);

			return parameters;
		}

	}

}
