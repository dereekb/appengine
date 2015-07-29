package com.dereekb.gae.model.crud.deprecated.extension.create;

import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Request used by {@link LinkingCreateService}.
 *
 * @author dereekb
 *
 * @param <T>
 *            Target model
 */
@Deprecated
public interface LinkingCreateRequest<T extends UniqueModel>
        extends CreateRequest<T> {

	/**
	 *
	 * @return Whether or not linking is required.
	 */
	public boolean isLinkingRequired();

}
