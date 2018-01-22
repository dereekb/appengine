package com.dereekb.gae.server.event.event;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link Event} with a {@link ModelKey} that specifies a specific, unique
 * event.
 *
 * @author dereekb
 *
 */
public interface UniqueEvent
        extends Event, UniqueModel {

}
