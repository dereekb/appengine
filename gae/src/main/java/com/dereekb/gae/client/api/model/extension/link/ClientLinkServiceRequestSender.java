package com.dereekb.gae.client.api.model.extension.link;

import com.dereekb.gae.client.api.model.shared.builder.SecuredClientModelRequestSender;
import com.dereekb.gae.model.extension.links.service.LinkServiceRequest;

/**
 * {@link ClientLinkService} and {@link SecuredClientModelRequestSender}
 * extension interface.
 * 
 * @author dereekb
 */
public interface ClientLinkServiceRequestSender
        extends ClientLinkService, SecuredClientModelRequestSender<LinkServiceRequest, ClientLinkServiceResponse> {

}
