package com.dereekb.gae.client.api.auth.model.context;

import com.dereekb.gae.client.api.auth.model.roles.ClientModelRolesServiceRequestSender;
import com.dereekb.gae.client.api.model.extension.link.ClientLinkService;
import com.dereekb.gae.client.api.model.shared.builder.SecuredClientModelRequestSender;

/**
 * {@link ClientLinkService} and {@link SecuredClientModelRequestSender}
 * extension interface.
 *
 * @author dereekb
 *
 * @deprecated Use {@link ClientModelRolesServiceRequestSender} instead.
 */
@Deprecated
public interface ClientModelRolesLoginTokenContextServiceRequestSender
        extends ClientModelRolesLoginTokenContextService,
        SecuredClientModelRequestSender<ClientModelRolesLoginTokenContextRequest, ClientModelRolesLoginTokenContextResponse> {

}
