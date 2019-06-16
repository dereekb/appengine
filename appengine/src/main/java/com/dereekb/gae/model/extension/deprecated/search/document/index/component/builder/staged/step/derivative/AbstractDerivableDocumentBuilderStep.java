package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative;

import com.dereekb.gae.model.extension.deprecated.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.model.extension.links.descriptor.UniqueDescribedModel;
import com.google.appengine.api.search.Document.Builder;

/**
 * {@link AbstractIncludableDocumentBuilderStep} extension that automatically
 * adds the {@link Descriptor} info to the {@link Builder}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractDerivableDocumentBuilderStep<T extends UniqueDescribedModel> extends AbstractIncludableDocumentBuilderStep<T> {

	public AbstractDerivableDocumentBuilderStep() {
		super();
	}

	public AbstractDerivableDocumentBuilderStep(String format, boolean inclusionStep) {
		super(format, inclusionStep);
	}

	// Identifier
	@Override
	protected final void performModelStep(T model,
	                                Builder builder) {
		this.performDescribedModelStep(model, builder);
		ModelDocumentBuilderUtility.addDescriptorInfo(this.format, model, builder);
	}

	/**
	 * Attaches fields that should be included to only a model's search
	 * document.
	 * <p>
	 * Does not need to include the descriptor info.
	 *
	 * @param model
	 *            Model. Never {@code null}.
	 * @param builder
	 *            {@link Builder}. Never {@code null}.
	 */
	protected void performDescribedModelStep(T model,
	                                         Builder builder) {
		// Nothing by default. Override.
	};

}
