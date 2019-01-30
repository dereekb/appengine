package com.dereekb.gae.client.api.service.sender.security;

import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;

/**
 * {@link ClientRequest} that has an authentication token attached.
 * 
 * @author dereekb
 *
 */
public interface SecuredClientRequest
        extends ClientRequest, EncodedLoginToken {

}
