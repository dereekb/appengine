package com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.create;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.web.exceptions.ApiRuntimeException;
import com.thevisitcompany.gae.deprecated.web.exceptions.ForbiddenModelRequestException;
import com.thevisitcompany.gae.deprecated.web.exceptions.UnavailableModelsRequestException;
import com.thevisitcompany.gae.deprecated.web.response.models.cruds.CreateResponse;

/**
 * Director class for creating objects.
 *
 * @author dereekb
 *
 * @param <T>
 * @param <K>
 */
public class ApiCreateDirector<T, K> {

	private ApiCreateDirectorDelegate<T, K> delegate;

	public CreateResponse<T, K> create(CreateRequest<T, K> createRequest)
	        throws ForbiddenModelRequestException,
	            UnavailableModelsRequestException,
	            ApiCreateMethodUnavailableException {

		CreateResponse<T, K> response = null;

		try {
			response = this.delegate.handleCreateRequest(createRequest);

			if (response == null) {
				throw ApiCreateMethodUnavailableException.with(createRequest);
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

	public ApiCreateDirectorDelegate<T, K> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(ApiCreateDirectorDelegate<T, K> delegate) {
		this.delegate = delegate;
	}
}
