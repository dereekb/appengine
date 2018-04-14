package com.dereekb.gae.client.api.auth.token;

import com.dereekb.gae.client.api.model.shared.builder.SecuredClientModelRequestSender;

/**
 * {@link ClientLoginTokenValidationService} and
 * {@link SecuredClientModelRequestSender}
 * extension interface.
 *
 * @author dereekb
 */
public interface ClientLoginTokenValidationServiceRequestSender
        extends ClientLoginTokenValidationService,
        SecuredClientModelRequestSender<ClientLoginTokenValidationRequest, ClientLoginTokenValidationResponse> {

}
