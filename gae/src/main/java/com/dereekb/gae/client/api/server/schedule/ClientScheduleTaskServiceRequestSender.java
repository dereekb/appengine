package com.dereekb.gae.client.api.server.schedule;

import com.dereekb.gae.client.api.model.extension.link.ClientLinkService;
import com.dereekb.gae.client.api.model.shared.builder.SecuredClientModelRequestSender;

/**
 * {@link ClientLinkService} and {@link SecuredClientModelRequestSender}
 * extension interface.
 *
 * @author dereekb
 */
public interface ClientScheduleTaskServiceRequestSender
        extends ClientScheduleTaskService,
        SecuredClientModelRequestSender<ClientScheduleTaskRequest, ClientScheduleTaskResponse> {

}
