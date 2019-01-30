package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative;

import java.util.List;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.search.document.SearchableUniqueModel;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.google.appengine.api.search.Document;

/**
 * {@link StagedDocumentBuilderStep} implementation that uses a
 * {@link IncludedDocumentBuilder} to apply derivative components to the
 * document being generated.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class IncludedDocumentBuilderStep<T extends SearchableUniqueModel>
        implements StagedDocumentBuilderStep<T> {

	private IncludedDocumentBuilder builder;
	private IncludedDocumentBuilderStepDelegate<T> delegate;

	public IncludedDocumentBuilderStep() {}

	public IncludedDocumentBuilderStep(IncludedDocumentBuilder builder,
	        IncludedDocumentBuilderStepDelegate<T> delegate) {
		this.builder = builder;
		this.delegate = delegate;
	}

	public IncludedDocumentBuilder getBuilder() {
		return this.builder;
	}

	public void setBuilder(IncludedDocumentBuilder builder) {
		this.builder = builder;
	}

	public IncludedDocumentBuilderStepDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(IncludedDocumentBuilderStepDelegate<T> delegate) {
		this.delegate = delegate;
	}

	// MARK: StagedDocumentBuilderStep
	@Override
	public void performStep(T model,
	                        Document.Builder builder) {
		List<Descriptor> descriptors = this.delegate.getIncludedModelDescriptors(model);

		for (Descriptor descriptor : descriptors) {
			this.builder.applyDerivativeComponent(descriptor, builder);
		}
	}

	@Override
	public String toString() {
		return "IncludedDocumentBuilderStep [builder=" + this.builder + ", delegate=" + this.delegate + "]";
	}

}
