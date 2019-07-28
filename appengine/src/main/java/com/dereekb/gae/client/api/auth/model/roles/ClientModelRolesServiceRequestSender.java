package com.dereekb.gae.client.api.auth.model.roles;

import com.dereekb.gae.client.api.model.extension.link.ClientLinkService;
import com.dereekb.gae.client.api.model.shared.builder.SecuredClientModelRequestSender;

/**
 * {@link ClientLinkService} and {@link SecuredClientModelRequestSender}
 * extension interface.
 *
 * @author dereekb
 */
public interface ClientModelRolesServiceRequestSender
        extends ClientModelRolesService,
        SecuredClientModelRequestSender<ClientModelRolesRequest, ClientModelRolesResponse> {

}
