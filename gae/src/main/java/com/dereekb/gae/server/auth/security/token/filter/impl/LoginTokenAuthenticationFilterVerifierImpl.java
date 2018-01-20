package com.dereekb.gae.server.auth.security.token.filter.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.app.token.filter.LoginTokenAuthenticationFilterAppLoginSecurityVerifierImpl;
import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.filter.LoginTokenAuthenticationFilterVerifier;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * {@link LoginTokenAuthenticationFilterVerifier} implementation that verifies
 * the service requests were sent from another app-engine service.
 *
 * @author dereekb
 *
 * @deprecated Replaced by
 *             {@link LoginTokenAuthenticationFilterAppLoginSecurityVerifierImpl}.
 */
@Deprecated
public class LoginTokenAuthenticationFilterVerifierImpl<T extends LoginToken>
        implements LoginTokenAuthenticationFilterVerifier<T> {

	public static final String TASKQUEUE_QUEUE_HEADER_KEY = "X-AppEngine-QueueName";
	public static final String INBOUND_ID_HEADER_KEY = "X-Appengine-Inbound-Appid";

	private static final Logger LOGGER = Logger.getLogger(LoginTokenAuthenticationFilterVerifierImpl.class.getName());

	// MARK: LoginTokenAuthenticationFilterVerifier
	@Override
	public void assertValidDecodedLoginToken(DecodedLoginToken<T> decodedLoginToken,
	                                         HttpServletRequest request)
	        throws TokenException {
		LoginPointerType type = decodedLoginToken.getLoginToken().getPointerType();

		switch (type) {
			case SYSTEM:
				this.assertSystemRequestIsFromSystem(decodedLoginToken, request);
				break;
			default:
				break;
		}
	}

	private void assertSystemRequestIsFromSystem(DecodedLoginToken<T> decodedLoginToken,
	                                             HttpServletRequest request) {
		Object taskqueue = request.getHeader(TASKQUEUE_QUEUE_HEADER_KEY);

		if (taskqueue == null) {
			Object inboundAppId = request.getHeader(INBOUND_ID_HEADER_KEY);

			if (inboundAppId != null) {
				LOGGER.log(Level.INFO, "System Login Token used by app: " + inboundAppId);
			} else {
				String address = request.getRemoteAddr();
				LOGGER.log(Level.SEVERE, "System Login Token used for non-taskqueue purposes from IP: " + address);
			}
		}

		/*
		 * TODO: Figure out a better system request assertion.
		 *
		 * Requests may pass between servers due to TaskQueue calls that may
		 * forward requests to other servers.
		 *
		 * There is no universal request header set for all requests issued by
		 * the google app engine server.
		 */
		/*
		 * Object inboundHeader = request.getAttribute(INBOUND_ID_HEADER_KEY);
		 *
		 * if (header != null) {
		 * // String applicationSubject = decodedLoginToken.getSubject();
		 *
		 * } else {
		 * LOGGER.log(Level.SEVERE,
		 * "System Login Token used from non-server location.");
		 * throw new
		 * TokenUnauthorizedException("System key used from an invalid location."
		 * );
		 * }
		 */
	}

}
