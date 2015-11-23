package com.dereekb.gae.model.extension.search.document.index.component.builder.impl.derivative;

import java.util.Dictionary;

import com.dereekb.gae.model.extension.search.document.index.component.builder.impl.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.google.appengine.api.search.Document.Builder;

/**
 * Implementation of {@link StagedDocumentBuilderStep} for retrieving a
 * derivative model from the target model.
 *
 * Uses a {@link Dictionary} of {@link DerivativeDocumentBuilderStep} to build
 * onto the input {@link Builder}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public final class DerivativeDocumentBuilder<T extends UniqueSearchModel>
        implements StagedDocumentBuilderStep<T> {

	private final DerivativeDocumentBuilderDelegate<T> delegate;
	private final Dictionary<String, DerivativeDocumentBuilderStep<T>> steps;

	public DerivativeDocumentBuilder(DerivativeDocumentBuilderDelegate<T> delegate,
	        Dictionary<String, DerivativeDocumentBuilderStep<T>> steps) {
		this.delegate = delegate;
		this.steps = steps;
	}

	public DerivativeDocumentBuilderDelegate<T> getDelegate() {
		return this.delegate;
	}

	public Dictionary<String, DerivativeDocumentBuilderStep<T>> getSteps() {
		return this.steps;
	}

	@Override
	public void performStep(T model,
	                          Builder builder) {
		try {
			String type = this.delegate.readDerivativeType(model);
			DerivativeDocumentBuilderStep<T> step = this.steps.get(type);

			if (step != null) {
				String identifier = this.delegate.readDerivativeIdentifier(model);
				step.updateBuilder(identifier, builder);
			}
		} catch (NoDerivativeTypeException e) {

		}
	}

}
