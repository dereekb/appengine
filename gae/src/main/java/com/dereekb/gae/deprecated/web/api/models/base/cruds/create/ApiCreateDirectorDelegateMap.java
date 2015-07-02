package com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.create;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.web.response.models.cruds.CreateResponse;
import com.thevisitcompany.gae.utilities.collections.map.CatchMap;

public class ApiCreateDirectorDelegateMap<T, K> extends CatchMap<ApiCreateDirectorDelegate<T, K>>
        implements ApiCreateDirectorDelegate<T, K> {
	
	@Override
	public CreateResponse<T, K> handleCreateRequest(CreateRequest<T, K> request)
	        throws ForbiddenObjectChangesException,
	            UnavailableObjectsException {
		String type = request.getType();

		CreateResponse<T, K> response = null;
		ApiCreateDirectorDelegate<T, K> delegate = this.get(type);

		if (delegate != null) {
			response = delegate.handleCreateRequest(request);
		}

		return response;
	}

}
