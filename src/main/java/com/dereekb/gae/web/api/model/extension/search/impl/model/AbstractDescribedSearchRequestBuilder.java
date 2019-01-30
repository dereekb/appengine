package com.dereekb.gae.web.api.model.extension.search.impl.model;

import java.util.Map;

import com.dereekb.gae.model.extension.search.document.search.model.DescriptorSearch;
import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractDescribedModelDocumentRequest;

/**
 * {@link AbstractSearchRequestBuilder} for described models that use a
 * {@link AbstractDescribedModelDocumentRequest}.
 *
 * @author dereekb
 *
 * @param <R>
 *            request type
 */
public abstract class AbstractDescribedSearchRequestBuilder<R extends AbstractDescribedModelDocumentRequest> extends AbstractSearchRequestBuilder<R> {

	private static final String DEFAULT_DESCRIPTOR_KEY = "descriptor";

	private String descriptorKey = DEFAULT_DESCRIPTOR_KEY;

	protected AbstractDescribedSearchRequestBuilder(Class<? extends R> requestType) {
		super(requestType);
	}

	@Override
	public void applyParameters(R request,
	                            Map<String, String> parameters) {
		this.applyNonDescriptorParameters(request, parameters);
		request.setDescriptor(DescriptorSearch.fromString(parameters.get(this.descriptorKey)));
	}

	public abstract void applyNonDescriptorParameters(R request,
	                                                  Map<String, String> parameters);

}
