package com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.delete;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.web.exceptions.ApiRuntimeException;
import com.thevisitcompany.gae.deprecated.web.exceptions.ForbiddenModelRequestException;
import com.thevisitcompany.gae.deprecated.web.exceptions.UnavailableModelsRequestException;
import com.thevisitcompany.gae.deprecated.web.response.models.cruds.DeleteResponse;

/**
 * Director class for deleting objects.
 *
 * @author dereekb
 *
 * @param <K>
 */
public class ApiDeleteDirector<K> {

	private ApiDeleteDirectorDelegate<K> delegate;

	public DeleteResponse<K> delete(DeleteRequest<K> deleteRequest)
	        throws ForbiddenModelRequestException,
	            UnavailableModelsRequestException,
	            ApiDeleteMethodUnavailableException {

		DeleteResponse<K> response = null;

		try {
			response = this.delegate.handleDeleteRequest(deleteRequest);

			if (response == null) {
				throw ApiDeleteMethodUnavailableException.with(deleteRequest);
			}
		} catch (ForbiddenObjectChangesException e) {
			throw new ForbiddenModelRequestException(response);
		} catch (UnavailableObjectsException e) {
			throw new UnavailableModelsRequestException(e, response);
		} catch (RuntimeException e) {
			throw new ApiRuntimeException(e);
		}

		return response;
	}

	public ApiDeleteDirectorDelegate<K> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(ApiDeleteDirectorDelegate<K> delegate) {
		this.delegate = delegate;
	}
}
