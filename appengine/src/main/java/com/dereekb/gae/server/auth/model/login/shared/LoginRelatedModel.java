package com.dereekb.gae.server.auth.model.login.shared;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.googlecode.objectify.Key;

/**
 * Interface for a {@link UniqueModel} that is related to a {@link Login}.
 *
 * @author dereekb
 *
 */
public interface LoginRelatedModel
        extends UniqueModel {

	/**
	 * Returns the related login.
	 *
	 * @return {@link Key}. May be {@code null}.
	 */
	public Key<Login> getLogin();

}
