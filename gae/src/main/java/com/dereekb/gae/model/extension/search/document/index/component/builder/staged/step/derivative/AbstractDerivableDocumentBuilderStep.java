package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative;

import com.dereekb.gae.model.extension.search.document.SearchableDescribedModel;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
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
public abstract class AbstractDerivableDocumentBuilderStep<T extends SearchableDescribedModel> extends AbstractIncludableDocumentBuilderStep<T> {

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
	 * document. Does not need to include the descriptor info.
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

	/**
	 * By default will include the model's identifier using the current format.
	 */
	@Override
	protected void performInclusionStep(T model,
	                                    Builder builder) {
		ModelKey key = null;

		if (model != null) {
			key = model.getModelKey();
		}

		ModelDocumentBuilderUtility.addId(this.format, key, builder);
	}

}
