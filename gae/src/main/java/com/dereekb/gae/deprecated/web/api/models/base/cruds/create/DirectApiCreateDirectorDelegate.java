package com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.create;

import java.util.List;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.service.CreateService;
import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.ModelConverter;
import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;
import com.thevisitcompany.gae.deprecated.web.response.models.cruds.CreateResponse;

/**
 * Default implementation of {@link DirectApiCreateDirectorDelegate} interface,
 * that converts data to the model type, then uses a {@link CreateService} to
 * create new elements.
 *
 * @author dereekb
 */
public final class DirectApiCreateDirectorDelegate<T extends KeyedModel<K>, K, A>
        implements ApiCreateDirectorDelegate<A, K> {

	private ModelConverter<T, A> converter;
	private CreateService<T, K> service;
	private String returnModelType;

	public DirectApiCreateDirectorDelegate() {}

	@Override
	public CreateResponse<A, K> handleCreateRequest(CreateRequest<A, K> createRequest)
	        throws ForbiddenObjectChangesException,
	            UnavailableObjectsException {

		CreateResponse<A, K> response = null;
		List<A> data = createRequest.getData();

		if (data != null && data.isEmpty() == false) {
			List<T> models = this.converter.convertToObjects(data);
			List<T> created = this.service.create(models);

			List<A> createdData = this.converter.convertToDataModels(created);
			response = new CreateResponse<A, K>(createdData);
		} else {
			throw new IllegalArgumentException("No data available to create from.");
		}

		return response;
	}

	public ModelConverter<T, A> getConverter() {
		return this.converter;
	}

	public void setConverter(ModelConverter<T, A> converter) {
		this.converter = converter;
	}

	public CreateService<T, K> getService() {
		return this.service;
	}

	public void setService(CreateService<T, K> service) {
		this.service = service;
	}

	public String getReturnModelType() {
		return this.returnModelType;
	}

	public void setReturnModelType(String returnModelType) {
		this.returnModelType = returnModelType;
	}
}
