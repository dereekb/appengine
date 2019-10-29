package com.dereekb.gae.server.notification.model.shared;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link UniqueModel} that implements {@link Notifiable}.
 *
 * @author dereekb
 *
 */
public interface NotifiableUniqueModel
        extends UniqueModel, Notifiable {

}
