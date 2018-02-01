package com.dereekb.gae.client.api.service.sender.security.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestData;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientRequest;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
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

	private EncodedLoginToken token;
	private ClientRequest request;

	private AuthenticationParameterBuilder authParameterBuilder;

	public SecuredClientRequestImpl(EncodedLoginToken token, ClientRequest request) {
		this(token, request, AuthenticationParameterServiceImpl.SINGLETON);
	}

	public SecuredClientRequestImpl(EncodedLoginToken token,
	        ClientRequest request,
	        AuthenticationParameterBuilder authParameterBuilder) {
		super();
		this.setToken(token);
		this.setRequest(request);
		this.setAuthParameterBuilder(authParameterBuilder);
	}

	public EncodedLoginToken getToken() {
		return this.token;
	}

	public void setToken(EncodedLoginToken token) {
		if (token == null) {
			throw new IllegalArgumentException("token cannot be null.");
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
		return new SecuredHeaders();
	}

	@Override
	public Parameters getParameters() {
		return this.request.getParameters();
	}

	@Override
	public ClientRequestData getData() {
		return this.request.getData();
	}

	// MARK:
	@Override
	public String getTokenSignature() {
		return this.token.getTokenSignature();
	}

	@Override
	public String getEncodedLoginToken() {
		return this.token.getEncodedLoginToken();
	}

	// MARK: SecuredClientRequest
	private class SecuredHeaders
	        implements Parameters {

		private transient Map<String, String> parameters;

		@Override
		public Map<String, String> getParameters() {

			if (this.parameters == null) {
				this.parameters = new HashMap<String, String>();

				Parameters basicHeaders = SecuredClientRequestImpl.this.request.getHeaders();

				if (basicHeaders != null) {
					this.parameters.putAll(basicHeaders.getParameters());
				}

				List<KeyedEncodedParameter> authParameter = SecuredClientRequestImpl.this.authParameterBuilder
				        .buildAuthenticationParameters(SecuredClientRequestImpl.this.token);
				ParameterUtility.putAll(this.parameters, authParameter);
			}

			return this.parameters;
		}

	}

}
