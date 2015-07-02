package com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.delete;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.web.response.models.cruds.DeleteResponse;

public interface ApiDeleteDirectorDelegate<K> {

	public DeleteResponse<K> handleDeleteRequest(DeleteRequest<K> deleteRequest)
	        throws ForbiddenObjectChangesException,
	            UnavailableObjectsException;

}
