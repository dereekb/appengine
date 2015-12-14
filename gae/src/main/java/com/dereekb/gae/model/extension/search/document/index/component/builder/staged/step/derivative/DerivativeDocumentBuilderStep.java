package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative;

import java.util.List;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.google.appengine.api.search.Document;

public class DerivativeDocumentBuilderStep<T extends UniqueSearchModel>
        implements StagedDocumentBuilderStep<T> {

	private DerivativeDocumentBuilder builder;
	private DerivativeDocumentBuilderStepDelegate<T> delegate;

	@Override
	public void performStep(T model,
	                        Document.Builder builder) {
		List<Descriptor> descriptors = this.delegate.getDerivativeDescriptors(model);

		for (Descriptor descriptor : descriptors) {
			this.builder.applyDerivativeComponent(descriptor, builder);
		}
	}

}
