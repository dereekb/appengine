package com.dereekb.gae.model.crud.deprecated.extension.create;

import com.dereekb.gae.model.crud.deprecated.extension.create.exception.UnavailableLinkTargetException;
import com.dereekb.gae.model.crud.services.components.CreateService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Extension of {@link CreateService} that also aims to link elements together.
 *
 * @author dereekb
 */
@Deprecated
public interface LinkingCreateService<T extends UniqueModel>
        extends CreateService<T> {

	/**
	 *
	 * @param request
	 * @return
	 * @throws AtomicOperationException
	 * @throws UnavailableLinkTargetException
	 */
	public CreateResponse<T> create(LinkingCreateRequest<T> request)
	        throws AtomicOperationException,
	            UnavailableLinkTargetException;

}
