package com.dereekb.gae.model.extension.inclusion.reader.impl;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.model.extension.inclusion.exception.InclusionTypeUnavailableException;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReaderAnalysis;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ModelInclusionReaderAnalysis} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelInclusionReaderAnalysisImpl<T extends UniqueModel>
        implements ModelInclusionReaderAnalysis<T> {

	private final T model;
	private final InclusionReaderAnalysisDelegate<T> delegate;

	public ModelInclusionReaderAnalysisImpl(T model, InclusionReaderAnalysisDelegate<T> delegate) {
		this.model = model;
		this.delegate = delegate;
	}

	// MARK: ModelInclusionReaderAnalysis
	@Override
	public T getAnalyzedModel() {
		return this.model;
	}

	// MARK: InclusionReaderAnalysis
	@Override
	public ModelKey getModelKey() {
		return this.model.getModelKey();
	}

	@Override
	public Set<String> getRelatedTypes() {
		return this.delegate.getRelatedTypes();
	}

	@Override
	public Collection<ModelKey> getKeysForType(String type) throws InclusionTypeUnavailableException {
		return this.delegate.getRelatedKeysForType(type, this.model);
	}

	@Override
	public String toString() {
		return "ModelInclusionReaderAnalysisImpl [model=" + this.model + ", delegate=" + this.delegate + "]";
	}

}
