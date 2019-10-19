package com.dereekb.gae.client.api.service.sender.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.response.impl.ClientResponseImpl;
import com.dereekb.gae.client.api.service.sender.ClientRequestSender;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.gae.GoogleAppEngineContextualFactory;
import com.dereekb.gae.utilities.gae.GoogleAppEngineUtility;
import com.dereekb.gae.utilities.gae.impl.GoogleAppEngineContextualFactoryImpl;
import com.dereekb.gae.utilities.misc.path.PathUtility;

/**
 * {@link GoogleAppEngineContextualFactory} for {@link ClientRequestSender}.
 * <p>
 * Is only useful in cases where a no-data-response is useful in a generic
 * sense. Request senders should really use their own modified implementations
 * depending on the current state.
 *
 * @author dereekb
 *
 */
public class ClientRequestSenderFactory extends GoogleAppEngineContextualFactoryImpl<ClientRequestSender> {

	private String domainUrl;
	private String baseApiUrl;
	private String developmentDomainUrl;

	public ClientRequestSenderFactory() {
		super(true);
		this.setTestSingleton(TestClientRequestSender.SINGLETON);
		// TODO: Set development singleton that modifies the request and points
		// them to the shared local development.
	}

	// MARK: Configuration
	public String getDomainUrl() {
		return this.domainUrl;
	}

	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}

	public String getBaseApiUrl() {
		return this.baseApiUrl;
	}

	public void setBaseApiUrl(String baseApiUrl) {
		if (StringUtility.isEmptyString(baseApiUrl)) {
			throw new IllegalArgumentException("baseApiUrl cannot be null or empty.");
		}

		this.baseApiUrl = baseApiUrl;
	}

	public String getDevelopmentDomainUrl() {
		return this.developmentDomainUrl;
	}

	public void setDevelopmentDomainUrl(String developmentDomainUrl) {
		this.developmentDomainUrl = developmentDomainUrl;
	}

	// MARK: Implementations
	@Override
	public ClientRequestSender getProductionSingleton() {
		ClientRequestSender singleton = super.getProductionSingleton();

		if (singleton == null) {
			singleton = this.tryMakeProductionSender();
		}

		return singleton;
	}

	@Override
	public ClientRequestSender getDevelopmentSingleton() {
		ClientRequestSender singleton = super.getDevelopmentSingleton();

		if (singleton == null) {
			singleton = this.tryMakeDevelopmentSender();
		}

		return singleton;
	}

	protected ClientRequestSender tryMakeProductionSender() {
		String productionBaseUrl = this.getProductionBaseUrl();

		if (productionBaseUrl == null) {
			return null;
		}

		return new ClientRequestSenderImpl(productionBaseUrl);
	}

	protected ClientRequestSender tryMakeDevelopmentSender() {
		String developmentBaseUrl = this.getDevelopmentBaseUrl();

		if (developmentBaseUrl == null) {
			return null;
		}

		return new ClientRequestSenderImpl(developmentBaseUrl);
	}

	protected String getProductionBaseUrl() {
		if (this.baseApiUrl == null) {
			return null;
		}

		String domainUrl = this.getProductionDomainUrl();
		return PathUtility.buildPath(domainUrl, this.baseApiUrl);
	}

	protected String getDevelopmentBaseUrl() {
		if (this.baseApiUrl == null || this.developmentDomainUrl == null) {
			return null;
		}

		return PathUtility.buildPath(this.developmentDomainUrl, this.baseApiUrl);
	}

	protected String getProductionDomainUrl() {
		if (this.domainUrl != null) {
			return this.domainUrl;
		} else {
			return GoogleAppEngineUtility.urlForCurrentService(false);
		}
	}

	// MARK: Test
	/**
	 * Test {@link ClientRequestSender} implementation.
	 *
	 * @author dereekb
	 *
	 */
	public static class TestClientRequestSender
	        implements ClientRequestSender {

		private static final Logger LOGGER = Logger.getLogger(TestClientRequestSender.class.getName());

		public static final TestClientRequestSender SINGLETON = new TestClientRequestSender();

		@Override
		public ClientResponse sendRequest(ClientRequest request)
		        throws ClientConnectionException,
		            ClientRequestFailureException {

			String dataString = null;

			if (request.getData() != null) {
				dataString = request.getData().getDataString();
			}

			LOGGER.log(Level.INFO,
			        "Sending client request: \n" + "TO: " + request.getUrl().getRelativeUrlPath().getPath() + "\n"
			                + "METHOD: " + request.getMethod() + "\n" + "HEADERS: "
			                + request.getHeaders() + "\n" + "PARAMETERS: " + request.getParameters() + "\n"
			                + "DATA: " + dataString + "\n" + "");

			return new ClientResponseImpl(200, "{}");
		}

	}

}
