package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative;

import java.util.List;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.google.appengine.api.search.Document;

/**
 * {@link StagedDocumentBuilderStep} implementation that uses a
 * {@link DerivativeDocumentBuilder} to apply derivative components to the
 * document being generated.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class DerivativeDocumentBuilderStep<T extends UniqueSearchModel>
        implements StagedDocumentBuilderStep<T> {

	private DerivativeDocumentBuilder builder;
	private DerivativeDocumentBuilderStepDelegate<T> delegate;

	public DerivativeDocumentBuilderStep() {}

	public DerivativeDocumentBuilderStep(DerivativeDocumentBuilder builder,
	        DerivativeDocumentBuilderStepDelegate<T> delegate) {
		this.builder = builder;
		this.delegate = delegate;
	}

	public DerivativeDocumentBuilder getBuilder() {
		return this.builder;
	}

	public void setBuilder(DerivativeDocumentBuilder builder) {
		this.builder = builder;
	}

	public DerivativeDocumentBuilderStepDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(DerivativeDocumentBuilderStepDelegate<T> delegate) {
		this.delegate = delegate;
	}

	// MARK: StagedDocumentBuilderStep
	@Override
	public void performStep(T model,
	                        Document.Builder builder) {
		List<Descriptor> descriptors = this.delegate.getDerivativeDescriptors(model);

		for (Descriptor descriptor : descriptors) {
			this.builder.applyDerivativeComponent(descriptor, builder);
		}
	}

	@Override
	public String toString() {
		return "DerivativeDocumentBuilderStep [builder=" + this.builder + ", delegate=" + this.delegate + "]";
	}

}
