package com.dereekb.gae.model.extension.links.deleter.impl;

import java.util.Collection;
import java.util.Map;

import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.model.crud.services.request.impl.DeleteRequestImpl;
import com.dereekb.gae.model.extension.links.deleter.LinkDeleterChangeType;
import com.dereekb.gae.model.extension.links.deleter.LinkDeleterServiceEntry;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkDeleterServiceEntry} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class LinkDeleterServiceEntryImpl<T extends UniqueModel>
        implements LinkDeleterServiceEntry {

	private String modelType;
	private Map<String, LinkDeleterChangeType> changesMap;
	private DeleteService<T> deleteService;

	public LinkDeleterServiceEntryImpl(String modelType,
	        Map<String, LinkDeleterChangeType> changesMap,
	        DeleteService<T> deleteService) {
		this.setModelType(modelType);
		this.setChangesMap(changesMap);
		this.setDeleteService(deleteService);
	}

	@Override
	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	@Override
	public Map<String, LinkDeleterChangeType> getChangesMap() {
		return this.changesMap;
	}

	public void setChangesMap(Map<String, LinkDeleterChangeType> changesMap) {
		this.changesMap = changesMap;
	}

	public DeleteService<T> getDeleteService() {
		return this.deleteService;
	}

	public void setDeleteService(DeleteService<T> deleteService) {
		this.deleteService = deleteService;
	}

	@Override
	public void deleteModels(Collection<ModelKey> keys) {
		DeleteRequestImpl request = new DeleteRequestImpl(keys);
		this.deleteService.delete(request);
	}

	@Override
	public String toString() {
		return "LinkDeleterServiceEntryImpl [modelType=" + this.modelType + ", changesMap=" + this.changesMap
		        + ", deleteService=" + this.deleteService + "]";
	}

}
