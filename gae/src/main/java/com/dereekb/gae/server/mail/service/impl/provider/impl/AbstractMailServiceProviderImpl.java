package com.dereekb.gae.server.mail.service.impl.provider.impl;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;

import com.dereekb.gae.server.mail.service.MailService;
import com.dereekb.gae.server.mail.service.MailServiceRequest;
import com.dereekb.gae.server.mail.service.MailUser;
import com.dereekb.gae.server.mail.service.exception.InvalidMailRequestException;
import com.dereekb.gae.server.mail.service.exception.MailSendFailureException;
import com.dereekb.gae.server.mail.service.exception.UnauthorizedSenderMailRequestException;
import com.dereekb.gae.server.mail.service.impl.provider.MailServiceProvider;
import com.dereekb.gae.server.mail.service.impl.provider.MailServiceProviderRequest;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.impl.FilterImpl;

/**
 * Abstract {@link MailService} implementation.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractMailServiceProviderImpl<I extends MailServiceProviderRequest, O>
        implements MailServiceProvider<I, O> {

	private MailUser defaultSender;
	private Filter<MailUser> authorizedUsersFilter;

	public AbstractMailServiceProviderImpl(MailUser defaultSender) {
		this(defaultSender, new FilterImpl<MailUser>(FilterResult.FAIL));
	}

	public AbstractMailServiceProviderImpl(MailUser defaultSender, Filter<MailUser> authorizedUsersFilter) {
		super();
		this.setDefaultSender(defaultSender);
		this.setAuthorizedUsersFilter(authorizedUsersFilter);
	}

	public MailUser getDefaultSender() {
		return this.defaultSender;
	}

	public void setDefaultSender(MailUser defaultSender) {
		if (defaultSender == null) {
			throw new IllegalArgumentException("defaultSender cannot be null.");
		}

		this.defaultSender = defaultSender;
	}

	public Filter<MailUser> getAuthorizedUsersFilter() {
		return this.authorizedUsersFilter;
	}

	public void setAuthorizedUsersFilter(Filter<MailUser> authorizedUsersFilter) {
		if (authorizedUsersFilter == null) {
			throw new IllegalArgumentException("authorizedUsersFilter cannot be null.");
		}

		this.authorizedUsersFilter = authorizedUsersFilter;
	}

	// MARK: MailService
	@Override
	public void sendMail(MailServiceRequest request) throws InvalidMailRequestException, MailSendFailureException {
		I providerRequest = this.convertGenericRequest(request);
		this.sendMail(providerRequest);
	}

	// MARK: MailServiceProvider
	@Override
	public final O sendMail(I input) throws MailSendFailureException {
		this.assertValidRequest(input);
		return this.makeInstance(input).sendRequest();
	}

	protected abstract I convertGenericRequest(MailServiceRequest request);

	protected void assertValidRequest(I input) {
		MailServiceRequest request = input.getMailServiceRequest();
		this.assertValidBaseRequest(request);
	}

	protected void assertValidBaseRequest(MailServiceRequest input) {
		MailUser mailUser = input.getSender();

		// Assert allowed mail user.
		if (mailUser != null) {
			boolean isAllowedUser = this.authorizedUsersFilter.filterObject(mailUser).bool;

			if (!isAllowedUser && this.defaultSender.equals(mailUser) == false) {
				throw new UnauthorizedSenderMailRequestException();
			}
		}

	}

	protected abstract SenderInstance makeInstance(I input);

	// MARK: Utility
	protected abstract class RestTemplateSenderInstance extends SenderInstance {

		public RestTemplateSenderInstance(I input) {
			super(input);
		}

		@Override
		public O sendRequest() throws MailSendFailureException {
			ResponseEntity<String> response = this.sendProviderRequest();
			return this.makeMailServiceResponse(response);
		}
		
		protected abstract ResponseEntity<String> sendProviderRequest() throws MailSendFailureException;
		
		protected abstract O makeMailServiceResponse(ResponseEntity<String> response);
		
		// MARK: Utility
		@Override
		protected MailUser getSenderForRequest() {
			MailServiceRequest request = this.input.getMailServiceRequest();
			MailUser sender = request.getSender();

			if (sender == null) {
				sender = AbstractMailServiceProviderImpl.this.defaultSender;
			}

			return sender;
		}

	}

	protected abstract class SenderInstance {

		protected final I input;

		public SenderInstance(I input) {
			this.input = input;
		}

		public abstract O sendRequest() throws MailSendFailureException;

		// MARK: Utility
		protected String getSenderString() {
			MailUser sender = this.getSenderForRequest();
			return makeSenderString(sender);
		}
		
		protected MailUser getSenderForRequest() {
			MailServiceRequest request = this.input.getMailServiceRequest();
			MailUser sender = request.getSender();

			if (sender == null) {
				sender = AbstractMailServiceProviderImpl.this.defaultSender;
			}

			return sender;
		}

	}

	// MARK: Static Utility
	protected static String makeSenderString(MailUser sender) {
		String name = sender.getName();
		String email = sender.getEmailAddress();
		
		if (name != null) {
			return "" + name + " <" + email + ">";
		} else {
			return email;
		}
	}
	
	protected static void addBasicAuthHeader(String username,
	                                         String password,
	                                         HttpHeaders headers) {
		String authHeader = makeBasicAuthHeader(username, password);
		headers.add("Authorization", authHeader);
	}

	protected static String makeBasicAuthHeader(String username,
	                                            String password) {
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(encodedAuth);
	}

}
