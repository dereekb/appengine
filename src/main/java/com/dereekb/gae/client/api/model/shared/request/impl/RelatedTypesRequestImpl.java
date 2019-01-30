package com.dereekb.gae.client.api.model.shared.request.impl;

import java.util.Set;

import com.dereekb.gae.client.api.model.shared.request.RelatedTypesRequest;

/**
 * {@link RelatedTypesRequest} implementation.
 * 
 * @author dereekb
 *
 */
public class RelatedTypesRequestImpl
        implements RelatedTypesRequest {

	protected boolean loadRelatedTypes;
	protected Set<String> relatedTypesFilter;

	public RelatedTypesRequestImpl() {
		this(false, null);
	}

	public RelatedTypesRequestImpl(boolean loadRelatedTypes) {
		this(loadRelatedTypes, null);
	}

	public RelatedTypesRequestImpl(boolean loadRelatedTypes, Set<String> relatedTypesFilter) {
		super();
		this.setLoadRelatedTypes(loadRelatedTypes);
		this.setRelatedTypesFilter(relatedTypesFilter);
	}

	@Override
	public boolean shouldLoadRelatedTypes() {
		return this.loadRelatedTypes;
	}

	public void setLoadRelatedTypes(boolean loadRelatedTypes) {
		this.loadRelatedTypes = loadRelatedTypes;
	}

	@Override
	public Set<String> getRelatedTypesFilter() {
		return this.relatedTypesFilter;
	}

	@Override
	public void setRelatedTypesFilter(Set<String> relatedTypesFilter) {
		this.relatedTypesFilter = relatedTypesFilter;
	}

	@Override
	public String toString() {
		return "RelatedTypesRequestImpl [loadRelatedTypes=" + this.loadRelatedTypes + ", relatedTypesFilter="
		        + this.relatedTypesFilter + "]";
	}

}
