package com.dereekb.gae.model.extension.search.document.service.impl;

import com.dereekb.gae.model.extension.search.document.service.TypedModelSearchServiceRequest;
import com.dereekb.gae.utilities.model.search.request.SearchRequest;
import com.dereekb.gae.utilities.model.search.request.impl.SearchRequestImpl;

/**
 * {@link TypedModelSearchServiceRequest} implementation.
 *
 * @author dereekb
 *
 */
public class TypedModelSearchServiceRequestImpl extends SearchRequestImpl
        implements TypedModelSearchServiceRequest {

	private String index;

	public TypedModelSearchServiceRequestImpl() throws IllegalArgumentException {
		super();
	}

	public TypedModelSearchServiceRequestImpl(String index) throws IllegalArgumentException {
		super();
		this.setIndex(index);
	}

	public TypedModelSearchServiceRequestImpl(SearchRequest request) throws IllegalArgumentException {
		super(request);
	}

	public TypedModelSearchServiceRequestImpl(String index, SearchRequest request) throws IllegalArgumentException {
		this(request);
		this.setIndex(index);
	}

	@Override
	public String getIndex() {
		return this.index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

}
