package com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.create;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.web.response.models.cruds.CreateResponse;

public interface ApiCreateDirectorDelegate<T, K> {

	public CreateResponse<T, K> handleCreateRequest(CreateRequest<T, K> createRequest)
	        throws ForbiddenObjectChangesException,
	            UnavailableObjectsException;

}
