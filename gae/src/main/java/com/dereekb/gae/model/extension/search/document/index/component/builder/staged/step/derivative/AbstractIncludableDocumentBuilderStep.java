package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.google.appengine.api.search.Document.Builder;

/**
 * {@link StagedDocumentBuilderStep} that is configured to update a
 * {@link Builder} instance depending on the type of request.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractIncludableDocumentBuilderStep<T extends UniqueSearchModel>
        implements StagedDocumentBuilderStep<T> {

	protected String format;
	protected boolean inclusionStep;

	public AbstractIncludableDocumentBuilderStep() {
		this("%s", true);
	}

	public AbstractIncludableDocumentBuilderStep(String format, boolean inclusionStep) {
		this.setFormat(format);
		this.setInclusionStep(inclusionStep);
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) throws IllegalArgumentException {
		if (format == null) {
			throw new IllegalArgumentException("Format cannot be null.");
		}

		this.format = format;
	}

	public boolean isInclusionStep() {
		return this.inclusionStep;
	}

	public void setInclusionStep(boolean isInclusionStep) {
		this.inclusionStep = isInclusionStep;
	}

	@Override
	public void performStep(T model,
	                        Builder builder) throws NullPointerException {
		this.performSharedStep(model, builder);

		if (this.inclusionStep) {
			this.performInclusionStep(model, builder);
		} else {
			this.performModelStep(model, builder);
		}

	}

	/**
	 * Attaches all fields that should be included to both an inclusion's and a
	 * model's search document.
	 *
	 * @param model
	 *            Included model. May be {@code null} if no model is set.
	 * @param builder
	 *            {@link Builder}. Never {@code null}.
	 */
	protected abstract void performSharedStep(T model,
	                                          Builder builder);

	/**
	 * Attaches fields that should be included to only a model's search
	 * document.
	 *
	 * @param model
	 *            Model. Never {@code null}.
	 * @param builder
	 *            {@link Builder}. Never {@code null}.
	 */
	protected abstract void performModelStep(T model,
	                                         Builder builder);

	/**
	 * Attaches all fields that should be included only an inclusion's search
	 * document.
	 *
	 * @param model
	 *            Included model. May be {@code null} if no model is set.
	 * @param builder
	 *            {@link Builder}. Never {@code null}.
	 */
	protected abstract void performInclusionStep(T model,
	                                              Builder builder);

}