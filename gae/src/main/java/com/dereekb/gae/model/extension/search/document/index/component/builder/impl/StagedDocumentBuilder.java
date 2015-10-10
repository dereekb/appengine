package com.dereekb.gae.model.extension.search.document.index.component.builder.impl;

import java.util.List;

import com.dereekb.gae.model.extension.search.document.index.component.builder.SearchDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.component.builder.StagedDocumentBuilderInit;
import com.dereekb.gae.model.extension.search.document.index.component.builder.StagedDocumentBuilderStep;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.google.appengine.api.search.Document;

/**
 * Implementation of {@link StagedDocumentBuilder} that uses multiple builders
 * in order.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public final class StagedDocumentBuilder<T extends UniqueSearchModel>
        implements SearchDocumentBuilder<T> {

	private final StagedDocumentBuilderInit<T> initializer;
	private final List<StagedDocumentBuilderStep<T>> steps;

	public StagedDocumentBuilder(StagedDocumentBuilderInit<T> initializer, List<StagedDocumentBuilderStep<T>> steps)
	        throws IllegalArgumentException {
		if (this.initializer == null) {
			throw new IllegalArgumentException("Initializer cannot be null.");
		}

		if (this.steps == null) {
			throw new IllegalArgumentException("Steps cannot be null.");
		}

		this.initializer = initializer;
		this.steps = steps;
	}

	public StagedDocumentBuilderInit<T> getInitializer() {
		return this.initializer;
	}

	public List<StagedDocumentBuilderStep<T>> getSteps() {
		return this.steps;
	}

	@Override
	public Document buildSearchDocument(T model) {
		Document.Builder builder = this.build(model);
		return builder.build();
	}

	public Document.Builder build(T model) {
		Document.Builder builder = this.initializer.newBuilder(model);

		for (StagedDocumentBuilderStep<T> step : this.steps) {
			step.updateBuilder(model, builder);
		}

		return builder;
	}

}
