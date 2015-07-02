package com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.delete;

import java.util.List;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.service.DeleteService;
import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;
import com.thevisitcompany.gae.deprecated.web.response.models.cruds.DeleteResponse;

/**
 * Default implementation of {@link DirectApiDeleteDirectorDelegate} interface,
 * that converts data to the model type, then uses a {@link DeleteService} to
 * delete new elements.
 *
 * @author dereekb
 */
public final class DirectApiDeleteDirectorDelegate<T extends KeyedModel<K>, K, A>
        implements ApiDeleteDirectorDelegate<K> {

	private DeleteService<T, K> service;
	private String returnModelType;

	public DirectApiDeleteDirectorDelegate() {}

	@Override
	public DeleteResponse<K> handleDeleteRequest(DeleteRequest<K> deleteRequest)
	        throws ForbiddenObjectChangesException,
	            UnavailableObjectsException {

		DeleteResponse<K> response = null;
		List<K> identifiers = deleteRequest.getData();

		if (identifiers != null && identifiers.isEmpty() == false) {
			List<K> deleted = this.service.deleteWithKey(identifiers);
			response = new DeleteResponse<K>(deleted);
		} else {
			throw new IllegalArgumentException("No identifiers available to delete from.");
		}

		return response;
	}

	public DeleteService<T, K> getService() {
		return this.service;
	}

	public void setService(DeleteService<T, K> service) {
		this.service = service;
	}

	public String getReturnModelType() {
		return this.returnModelType;
	}

	public void setReturnModelType(String returnModelType) {
		this.returnModelType = returnModelType;
	}
}
