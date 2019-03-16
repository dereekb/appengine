package com.dereekb.gae.model.extension.inclusion.reader.impl;

import java.util.Collection;

import com.dereekb.gae.model.extension.inclusion.exception.InclusionTypeUnavailableException;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReaderAnalysis;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapWithSet;
import com.dereekb.gae.utilities.collections.set.CaseInsensitiveSet;

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

	private T model;
	private CaseInsensitiveMapWithSet<ModelKey> relatedKeys;

	public ModelInclusionReaderAnalysisImpl(T model, CaseInsensitiveMapWithSet<ModelKey> relatedKeys) {
		this.model = model;
		this.relatedKeys = relatedKeys;
	}

	public T getModel() {
		return this.model;
	}

	public void setModel(T model) {
		if (model == null) {
			throw new IllegalArgumentException("model cannot be null.");
		}

		this.model = model;
	}

	public CaseInsensitiveMapWithSet<ModelKey> getRelatedKeys() {
		return this.relatedKeys;
	}

	public void setRelatedKeys(CaseInsensitiveMapWithSet<ModelKey> relatedKeys) {
		if (relatedKeys == null) {
			throw new IllegalArgumentException("relatedKeys cannot be null.");
		}

		this.relatedKeys = relatedKeys;
	}

	// MARK: ModelInclusionReaderAnalysis
	@Override
	public ModelKey getModelKey() {
		return this.model.getModelKey();
	}

	@Override
	public CaseInsensitiveSet getRelatedTypes() {
		return this.relatedKeys.keySet();
	}

	@Override
	public Collection<ModelKey> getKeysForType(String type) throws InclusionTypeUnavailableException {
		return this.relatedKeys.get(type);
	}

	@Override
	public CaseInsensitiveMapWithSet<ModelKey> getRelationMap() {
		return this.relatedKeys;
	}

	@Override
	public T getAnalyzedModel() {
		return this.model;
	}

	@Override
	public String toString() {
		return "ModelInclusionReaderAnalysisImpl [model=" + this.model + ", relatedKeys=" + this.relatedKeys + "]";
	}

}
