package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.impl;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.model.extension.search.document.SearchableUniqueModel;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.ModelStagedDocumentBuilderStep;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.google.appengine.api.search.Document.Builder;

/**
 * {@link ModelStagedDocumentBuilderStep} implementation that uses a
 * {@link Getter} and a {@link StagedDocumentBuilderStep}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelStagedDocumentBuilderStepImpl<T extends SearchableUniqueModel>
        implements ModelStagedDocumentBuilderStep, StagedDocumentBuilderStep<T> {

	private Getter<T> getter;
	private StagedDocumentBuilderStep<T> step;

	public ModelStagedDocumentBuilderStepImpl() {}

	public ModelStagedDocumentBuilderStepImpl(Getter<T> getter, StagedDocumentBuilderStep<T> step) {
		this.getter = getter;
		this.step = step;
	}

	public Getter<T> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<T> getter) {
		this.getter = getter;
	}

	public StagedDocumentBuilderStep<T> getStep() {
		return this.step;
	}

	public void setStep(StagedDocumentBuilderStep<T> step) {
		this.step = step;
	}

	// MARK: ModelStagedDocumentBuilderStep
	@Override
	public void performStep(ModelKey key,
	                        Builder builder) throws UnavailableModelException {
		T model = this.getter.get(key);

		try {
			this.performStep(model, builder);
		} catch (UnavailableModelException e) {
			throw new UnavailableModelException(key);
		}
	}

	// MARK: StagedDocumentBuilderStep
	@Override
	public void performStep(T model,
	                        Builder builder) throws UnavailableModelException {
		this.step.performStep(model, builder);
	}

	@Override
	public String toString() {
		return "ModelStagedDocumentBuilderStepImpl [getter=" + this.getter + ", step=" + this.step + "]";
	}

}
