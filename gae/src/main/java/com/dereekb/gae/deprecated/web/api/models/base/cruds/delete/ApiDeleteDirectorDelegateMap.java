package com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.delete;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.web.response.models.cruds.DeleteResponse;
import com.thevisitcompany.gae.utilities.collections.map.CatchMap;

public class ApiDeleteDirectorDelegateMap<K> extends CatchMap<ApiDeleteDirectorDelegate<K>>
        implements ApiDeleteDirectorDelegate<K> {

	@Override
	public DeleteResponse<K> handleDeleteRequest(DeleteRequest<K> request)
	        throws ForbiddenObjectChangesException,
	            UnavailableObjectsException {
		String type = request.getType();

		DeleteResponse<K> response = null;
		ApiDeleteDirectorDelegate<K> delegate = this.get(type);

		if (delegate != null) {
			response = delegate.handleDeleteRequest(request);
		}

		return response;
	}

}
