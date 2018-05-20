package com.dereekb.gae.client.api.auth.system;

import com.dereekb.gae.client.api.model.shared.builder.SecuredClientModelRequestSender;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenRequest;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenResponse;

/**
 * {@link ClientSystemLoginTokenService} and
 * {@link SecuredClientModelRequestSender}
 * extension interface.
 *
 * @author dereekb
 */
public interface ClientSystemLoginTokenServiceRequestSender
        extends ClientSystemLoginTokenService,
        SecuredClientModelRequestSender<SystemLoginTokenRequest, SystemLoginTokenResponse> {

}
