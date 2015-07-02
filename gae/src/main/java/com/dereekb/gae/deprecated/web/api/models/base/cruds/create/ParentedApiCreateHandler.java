package com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.create;

import java.util.List;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.ModelConverter;
import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;
import com.thevisitcompany.gae.deprecated.web.response.models.cruds.CreateResponse;

/**
 * Default create implementation for elements that have a single parent.
 * Separates the names and data, then creates new elements based off the
 * information provided using a delegate.
 *
 * @author dereekb
 *
 * @param <T>
 * @param <K>
 * @param <A>
 */
public class ParentedApiCreateHandler<T extends KeyedModel<K>, K, A>
        implements ApiCreateDirectorDelegate<A, K> {

	private ModelConverter<T, A> converter;
	private ParentedApiCreateHandlerDelegate<T, K> delegate;
	private String returnModelType;

	public ParentedApiCreateHandler() {}

	@Override
	public CreateResponse<A, K> handleCreateRequest(CreateRequest<A, K> createRequest)
	        throws ForbiddenObjectChangesException,
	            UnavailableObjectsException {

		CreateResponse<A, K> response = null;
		K parent = createRequest.getParent();

		if (parent != null) {
			List<A> data = createRequest.getData();
			List<String> names = createRequest.getNames();
			List<T> created = null;

			if (data != null) {
				List<T> sources = this.converter.convertToObjects(data);
				created = this.delegate.createWithSources(parent, sources);
			} else if (names != null && names.isEmpty() == false) {
				created = this.delegate.createWithNames(parent, names);
			} else {
				throw new IllegalArgumentException("Required name or data was not given.");
			}

			List<A> createdData = this.converter.convertToDataModels(created, this.returnModelType);
			response = new CreateResponse<A, K>(createdData);
		} else {
			throw new IllegalArgumentException("Required parent identifier was not specified.");
		}

		return response;
	}

	public ModelConverter<T, A> getConverter() {
		return this.converter;
	}

	public void setConverter(ModelConverter<T, A> converter) {
		this.converter = converter;
	}

	public ParentedApiCreateHandlerDelegate<T, K> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(ParentedApiCreateHandlerDelegate<T, K> delegate) {
		this.delegate = delegate;
	}

	public String getReturnModelType() {
		return this.returnModelType;
	}

	public void setReturnModelType(String returnModelType) {
		this.returnModelType = returnModelType;
	}

}
