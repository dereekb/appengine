package com.dereekb.gae.server.mail.service.impl.provider.mailgun.impl;

import java.util.Arrays;
import java.util.Set;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.dereekb.gae.server.mail.service.MailRecipient;
import com.dereekb.gae.server.mail.service.MailRecipientType;
import com.dereekb.gae.server.mail.service.MailServiceRequest;
import com.dereekb.gae.server.mail.service.MailServiceRequestBody;
import com.dereekb.gae.server.mail.service.MailServiceRequestBodyType;
import com.dereekb.gae.server.mail.service.MailUser;
import com.dereekb.gae.server.mail.service.exception.MailSendFailureException;
import com.dereekb.gae.server.mail.service.impl.provider.impl.AbstractMailServiceProviderImpl;
import com.dereekb.gae.server.mail.service.impl.provider.mailgun.MailgunMailService;
import com.dereekb.gae.server.mail.service.impl.provider.mailgun.MailgunMailServiceConfiguration;
import com.dereekb.gae.server.mail.service.impl.provider.mailgun.MailgunMailServiceRequest;
import com.dereekb.gae.server.mail.service.impl.provider.mailgun.MailgunMailServiceResponse;
import com.dereekb.gae.utilities.filters.Filter;

/**
 * {@link MailService} configured for Mailgun.
 * <p>
 * https://documentation.mailgun.com/en/latest/api-intro.html
 * 
 * @author dereekb
 *
 */
public class MailgunMailServiceImpl extends AbstractMailServiceProviderImpl<MailgunMailServiceRequest, MailgunMailServiceResponse>
        implements MailgunMailService {

	private static final String MAILGUN_AUTH_HEADER_USER = "api";
	private static final String MAILGUN_MESSAGE_POST_URL_FORMAT = "https://api.mailgun.net/v3/%s/messages";

	private MailgunMailServiceConfiguration configuration;

	public MailgunMailServiceImpl(MailUser defaultSender, MailgunMailServiceConfiguration configuration) {
		super(defaultSender);
		this.setConfiguration(configuration);
	}

	public MailgunMailServiceImpl(MailUser defaultSender,
	        Filter<MailUser> authorizedUsersFilter,
	        MailgunMailServiceConfiguration configuration) {
		super(defaultSender, authorizedUsersFilter);
		this.setConfiguration(configuration);
	}

	public MailgunMailServiceConfiguration getConfiguration() {
		return this.configuration;
	}

	public void setConfiguration(MailgunMailServiceConfiguration configuration) {
		if (configuration == null) {
			throw new IllegalArgumentException("configuration cannot be null.");
		}

		this.configuration = configuration;
	}

	public String getApiKey() {
		return this.configuration.getApiKey();
	}

	public String getDomain() {
		return this.configuration.getDomain();
	}

	// MARK: Mail Service
	@Override
	protected MailgunMailServiceRequest convertGenericRequest(MailServiceRequest request) {
		return new MailgunMailServiceRequestImpl(request);
	}

	@Override
	protected MailgunSenderInstance makeInstance(MailgunMailServiceRequest input) {
		return new MailgunSenderInstance(input);
	}

	protected class MailgunSenderInstance extends RestTemplateSenderInstance {

		public MailgunSenderInstance(MailgunMailServiceRequest input) {
			super(input);
		}

		// MARK: RestTemplateSenderInstance
		@Override
		protected ResponseEntity<String> sendProviderRequest() throws MailSendFailureException {

			// Configure Headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			addBasicAuthHeader(MAILGUN_AUTH_HEADER_USER, MailgunMailServiceImpl.this.getApiKey(), headers);

			// Configure Request Map
			MultiValueMap<String, String> map = this.makeMapForRequest();

			// Send Request
			RestTemplate restTemplate = new RestTemplate();
			String url = String.format(MAILGUN_MESSAGE_POST_URL_FORMAT, MailgunMailServiceImpl.this.getDomain());
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map,
			        headers);

			try {
				return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			} catch (HttpClientErrorException e) {
				String responseBody = e.getResponseBodyAsString();
				throw new MailSendFailureException(e);
			}
		}

		protected MultiValueMap<String, String> makeMapForRequest() {
			MailServiceRequest request = this.input.getMailServiceRequest();
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

			// Add To/From
			map.add("from", this.getSenderString());

			Set<MailRecipient> recipients = request.getRecipients();
			for (MailRecipient recipient : recipients) {
				MailRecipientType type = recipient.getRecipientType();
				String typeString = type.toString().toLowerCase();
				map.add(typeString, recipient.getEmailAddress());
			}

			// Add Body
			MailServiceRequestBody body = request.getBody();
			String subject = body.getSubject();

			MailServiceRequestBodyType contentType = body.getBodyType();
			String content = body.getBodyContent();
			String contentTypeKey = (contentType == MailServiceRequestBodyType.PLAIN_TEXT) ? "text" : "html";

			map.add("subject", subject);
			map.add(contentTypeKey, content);

			// TODO: Add Attachments
			
			// Add Custom Configuration
			if (MailgunMailServiceImpl.this.configuration.isTestMode()) {
				map.add("o:testmode", "true");
			}

			return map;
		}

		@Override
		protected MailgunMailServiceResponse makeMailServiceResponse(ResponseEntity<String> response) {
			return new MailgunMailServiceResponse() {};
		}

	}

	@Override
	public String toString() {
		return "MailgunMailServiceImpl [configuration=" + this.configuration + ", getDefaultSender()="
		        + this.getDefaultSender() + ", getAuthorizedUsersFilter()=" + this.getAuthorizedUsersFilter() + "]";
	}

}
