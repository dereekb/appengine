package com.dereekb.gae.server.event.model.shared.webhook;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.model.shared.event.ModelEvent;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEvent;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.service.WebHookEventSerializer;

/**
 * Used for serializing {@link ModelEvent} and {@link ModelKeyEvent} instances
 * to a {@link WebHookEvent} for a specific type.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelWebHookEventSerializer<T extends UniqueModel>
        extends WebHookEventSerializer {

	public WebHookEvent convertSingle(Event input,
	                                  boolean keysOnly)
	        throws ConversionFailureException;

}
