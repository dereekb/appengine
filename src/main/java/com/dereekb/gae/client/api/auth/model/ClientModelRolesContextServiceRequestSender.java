package com.dereekb.gae.client.api.auth.model;

import com.dereekb.gae.client.api.model.extension.link.ClientLinkService;
import com.dereekb.gae.client.api.model.shared.builder.SecuredClientModelRequestSender;

/**
 * {@link ClientLinkService} and {@link SecuredClientModelRequestSender}
 * extension interface.
 *
 * @author dereekb
 */
public interface ClientModelRolesContextServiceRequestSender
        extends ClientModelRolesContextService,
        SecuredClientModelRequestSender<ClientModelRolesLoginTokenContextRequest, ClientModelRolesLoginTokenContextResponse> {

}
