package com.dereekb.gae.web.api.model.crud.controller.impl;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.impl.TypedModelImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.model.crud.controller.ReadControllerEntryRequest;

/**
 * {@link ReadControllerEntryRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ReadControllerEntryRequestImpl extends TypedModelImpl
        implements ReadControllerEntryRequest {

	private boolean atomic;
	private Collection<ModelKey> modelKeys;

	private boolean loadRelatedTypes;
	private Set<String> relatedTypesFilter;

	public ReadControllerEntryRequestImpl(String modelType, boolean atomic, Collection<ModelKey> modelKeys) {
		this(modelType, atomic, modelKeys, false, null);
	}

	public ReadControllerEntryRequestImpl(String modelType,
	        boolean atomic,
	        Collection<ModelKey> modelKeys,
	        boolean loadRelatedTypes,
	        Set<String> relatedTypesFilter) {
		super(modelType);
		this.setAtomic(atomic);
		this.setModelKeys(modelKeys);
		this.setLoadRelatedTypes(loadRelatedTypes);
		this.setRelatedTypesFilter(relatedTypesFilter);
	}

	@Override
	public boolean isAtomic() {
		return this.atomic;
	}

	public void setAtomic(boolean atomic) {
		this.atomic = atomic;
	}

	@Override
	public Collection<ModelKey> getModelKeys() {
		return this.modelKeys;
	}

	public void setModelKeys(Collection<ModelKey> modelKeys) {
		if (modelKeys == null) {
			throw new IllegalArgumentException("modelKeys cannot be null.");
		}

		this.modelKeys = modelKeys;
	}

	public boolean isLoadRelatedTypes() {
		return this.loadRelatedTypes;
	}

	@Override
	public boolean loadRelatedTypes() {
		return this.loadRelatedTypes;
	}

	public void setLoadRelatedTypes(boolean loadRelatedTypes) {
		this.loadRelatedTypes = loadRelatedTypes;
	}

	@Override
	public Set<String> getRelatedTypesFilter() {
		return this.relatedTypesFilter;
	}

	public void setRelatedTypesFilter(Set<String> relatedTypesFilter) {
		this.relatedTypesFilter = relatedTypesFilter;
	}

	@Override
	public String toString() {
		return "ReadControllerEntryRequestImpl [modelType=" + this.modelType + ", atomic=" + this.atomic
		        + ", modelKeys=" + this.modelKeys + ", loadRelatedTypes=" + this.loadRelatedTypes
		        + ", relatedTypesFilter=" + this.relatedTypesFilter + "]";
	}

}
