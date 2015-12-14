package com.dereekb.gae.model.extension.search.document.index.component.builder.staged;

import java.util.List;

import com.dereekb.gae.model.extension.search.document.index.component.builder.SearchDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.initializer.StagedDocumentBuilderInitializer;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
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
public class StagedDocumentBuilder<T extends UniqueSearchModel>
        implements SearchDocumentBuilder<T> {

	private StagedDocumentBuilderInitializer<T> initializer;
	private List<StagedDocumentBuilderStep<T>> steps;

	public StagedDocumentBuilder(StagedDocumentBuilderInitializer<T> initializer, List<StagedDocumentBuilderStep<T>> steps)
	        throws IllegalArgumentException {
		this.setInitializer(initializer);
		this.setSteps(steps);
	}

	public StagedDocumentBuilderInitializer<T> getInitializer() {
		return this.initializer;
	}

	public void setInitializer(StagedDocumentBuilderInitializer<T> initializer) {

		if (initializer == null) {
			throw new IllegalArgumentException("Initializer cannot be null.");
		}

		this.initializer = initializer;
	}

	public List<StagedDocumentBuilderStep<T>> getSteps() {
		return this.steps;
	}

	public void setSteps(List<StagedDocumentBuilderStep<T>> steps) {

		if (steps == null) {
			throw new IllegalArgumentException("Steps cannot be null.");
		}

		this.steps = steps;
	}

	// MARK: SearchDocumentBuilder
	@Override
	public Document buildSearchDocument(T model) {
		Document.Builder builder = this.build(model);
		return builder.build();
	}

	public Document.Builder build(T model) {
		Document.Builder builder = this.initializer.newBuilder(model);

		for (StagedDocumentBuilderStep<T> step : this.steps) {
			step.performStep(model, builder);
		}

		return builder;
	}

}
