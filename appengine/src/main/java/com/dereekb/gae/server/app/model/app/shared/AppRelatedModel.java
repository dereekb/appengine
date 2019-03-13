package com.dereekb.gae.server.app.model.app.shared;

import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.googlecode.objectify.Key;

/**
 * Interface for a {@link UniqueModel} that is related to an {@link App}.
 *
 * @author dereekb
 *
 */
public interface AppRelatedModel
        extends UniqueModel {

	/**
	 * Returns the related app.
	 *
	 * @return {@link Key}. May be {@code null}.
	 */
	public Key<App> getApp();

}
