package com.dereekb.gae.web.api.model.controller.impl;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.model.controller.ReadControllerEntryRequest;

/**
 * {@link ReadControllerEntryRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ReadControllerEntryRequestImpl
        implements ReadControllerEntryRequest {

	private String modelType;
	private boolean atomic;
	private Collection<ModelKey> modelKeys;
	private boolean loadRelatedTypes;
	private Set<String> relatedTypesFilter;

	public ReadControllerEntryRequestImpl(String modelType, boolean atomic, Collection<ModelKey> modelKeys) {
		this.modelType = modelType;
		this.atomic = atomic;
		this.modelKeys = modelKeys;
	}

	public ReadControllerEntryRequestImpl(String modelType,
	        boolean atomic,
	        Collection<ModelKey> modelKeys,
	        boolean loadRelatedTypes,
	        Set<String> relatedTypesFilter) {
		this.modelType = modelType;
		this.atomic = atomic;
		this.modelKeys = modelKeys;
		this.loadRelatedTypes = loadRelatedTypes;
		this.relatedTypesFilter = relatedTypesFilter;
	}

	@Override
	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
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
		this.modelKeys = modelKeys;
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
