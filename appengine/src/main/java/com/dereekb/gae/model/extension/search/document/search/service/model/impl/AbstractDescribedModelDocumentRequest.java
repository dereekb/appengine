package com.dereekb.gae.model.extension.search.document.search.service.model.impl;

import com.dereekb.gae.model.extension.search.document.search.model.DescriptorSearch;

/**
 * {@link AbstractModelDocumentRequest} with a {@link DescriptorSearch}.
 *
 * @author dereekb
 * 
 * @see AbstractDescribedModelDocumentRequestConverter
 */
public class AbstractDescribedModelDocumentRequest extends AbstractModelDocumentRequest {

	protected DescriptorSearch descriptor;

	public AbstractDescribedModelDocumentRequest() {}

	public AbstractDescribedModelDocumentRequest(DescriptorSearch descriptor) {
		this.descriptor = descriptor;
	}

	public DescriptorSearch getDescriptor() {
		return this.descriptor;
	}

	public void setDescriptor(DescriptorSearch descriptor) {
		this.descriptor = descriptor;
	}

	@Override
	public String toString() {
		return "AbstractDescribedModelDocumentRequest [descriptor=" + this.descriptor + "]";
	}

}
