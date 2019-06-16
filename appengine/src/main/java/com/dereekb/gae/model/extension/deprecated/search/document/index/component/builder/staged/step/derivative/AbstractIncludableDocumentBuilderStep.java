package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.model.extension.deprecated.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.deprecated.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
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
public abstract class AbstractIncludableDocumentBuilderStep<T extends UniqueModel>
        implements StagedDocumentBuilderStep<T> {

	protected String format;
	protected boolean inclusionStep;

	public AbstractIncludableDocumentBuilderStep() {
		this("%s", false);
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
	                        Builder builder) throws UnavailableModelException {
		this.performSharedStep(model, builder);

		if (this.inclusionStep) {
			this.performInclusionStep(model, builder);
		} else if (model != null) {
			this.performModelStep(model, builder);
		} else {
			throw new UnavailableModelException("Model is required for non-inclusion steps.");
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
	protected void performInclusionStep(T model,
	                                    Builder builder) {
		ModelKey key = null;

		if (model != null) {
			key = model.getModelKey();
		}

		ModelDocumentBuilderUtility.addId(this.format, key, builder);
	}

}
